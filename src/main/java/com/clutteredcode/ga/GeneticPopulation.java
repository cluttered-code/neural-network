/*
 * Copyright © 2015 David Clutter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clutteredcode.ga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author David Clutter
 */
public abstract class GeneticPopulation<T extends GeneticIndividual<I>, I> {

    private static final Logger LOG = LoggerFactory.getLogger(GeneticPopulation.class);

    private transient final List<T> rankings;

    private final int population;
    private final int elites;
    private final double mutationRate;

    private transient List<T> generation;

    public GeneticPopulation(final int population, final int elites, final double mutationRate) {
        this.population = population;
        this.elites = elites;
        this.mutationRate = mutationRate;

        rankings = new ArrayList<>(population);

        initializeGeneration();
    }

    protected abstract void initializeGeneration();

    public void trainNumberOfGenerations(final int generations, final I inputs) {
        IntStream.rangeClosed(1, generations)
                .forEach(i -> {
                    LOG.info("training generation {} of {}", i, generations);
                    trainGeneration(inputs);
                });
    }

    public void trainUntilFitnessReached(final double goal, final I inputs) {
        double fitness = rankings.get(0).fitness();
        LOG.info("fitness {} of goal {}", fitness, goal);

        while (fitness < goal) {
            trainGeneration(inputs);

            fitness = rankings.get(0).fitness();
            LOG.info("fitness {} of goal {}", fitness, goal);
        }
    }

    private void trainGeneration(final I inputs) {
        LOG.info("################ Training Generation ################");
        trainAndRankAllIndividuals(inputs);
        crossoverGeneration();
        mutateGeneration();
    }

    private void trainAndRankAllIndividuals(final I inputs) {
        final PriorityQueue<T> rankingsHeap = new PriorityQueue<>(population);

        LOG.debug("training individuals");
        generation.forEach(individual -> {
            LOG.trace("train individual: {}", individual);
            individual.train(inputs);
            rankingsHeap.offer(individual);
        });

        populateSortedRankingsWithHeap(rankingsHeap);
    }

    private void populateSortedRankingsWithHeap(final PriorityQueue<T> rankingsHeap) {
        rankings.clear();

        LOG.debug("Sorting individuals by rank");
        while (!rankingsHeap.isEmpty()) {
            final T rankedIndividual = rankingsHeap.poll();
            rankings.add(rankedIndividual);
        }
    }

    private void crossoverGeneration() {
        final List<T> nextGeneration = new LinkedList<>(generation.subList(0, elites));
        final double totalFitness = adjustedTotalFitness();

        LOG.trace("{} elite individual(s) added to the next generation", elites);

        LOG.debug("crossover individuals...");
        IntStream.range(elites, population)
                .forEach(i -> {
                    final T newIndividual = selectAndCrossoverAPairOfIndividuals(totalFitness);
                    nextGeneration.add(newIndividual);
                });

        generation = nextGeneration;
    }


    /**
     * Adjusts fitness scale so the minimum fitness is 0 allowing for
     * fitness proportionate (roulette wheel) selection.
     *
     * @return The total adjusted fitness
     */
    private double adjustedTotalFitness() {
        final double fitnessOffset = fitnessOffset();

        LOG.trace("finding total fitness adjusting for offset");
        return rankings.parallelStream()
                .mapToDouble(individual -> individual.fitness() - fitnessOffset)
                .sum();
    }

    private double fitnessOffset() {
        return rankings.get(rankings.size() - 1).fitness();
    }

    private T selectAndCrossoverAPairOfIndividuals(final double totalFitness) {
        final T mother = selectIndividualUsingFitnessProportion(totalFitness);
        final T father = selectIndividualUsingFitnessProportion(totalFitness);

        LOG.trace("crossover {} with {}", mother, father);

        @SuppressWarnings("unchecked")
        final T newIndividual = (T) mother.crossover(father);

        return newIndividual;
    }

    private T selectIndividualUsingFitnessProportion(final double totalFitness) {
        final double random = Math.random();
        LOG.debug("fitness proportion selection random: {}", random);
        final double fitnessOffset = fitnessOffset();
        LOG.debug("fitness offset: {}", fitnessOffset);

        T selectedIndividual = null;
        double fitnessSum = 0;
        for (final T individual : rankings) {
            selectedIndividual = individual;
            final double fitness = individual.fitness();
            LOG.trace("fitness: {}", fitness);
            fitnessSum += individual.fitness() - fitnessOffset;
            LOG.trace("fitnessSum: {}", fitnessSum);
            final double fitnessProportion = fitnessSum / totalFitness;
            if (fitnessProportion > random) {
                LOG.debug("fitnessProportion: {}", fitnessProportion);
                break;
            } else {
                LOG.trace("fitnessProportion: {}", fitnessProportion);
            }
        }

        LOG.debug("selected individual: {}", selectedIndividual);
        return selectedIndividual;
    }

    private void mutateGeneration() {
        final List<T> eliteIndividuals = generation.subList(0, elites);
        LOG.trace("mutation ignoring {} elite individual(s)", elites);

        LOG.debug("mutating remaining individuals...");
        @SuppressWarnings("unchecked")
        final List<T> mutatedIndividuals = generation.stream()
                .skip(elites)
                .map(individual -> (T) individual.mutate(mutationRate))
                .collect(Collectors.toList());

        generation = Stream.of(eliteIndividuals, mutatedIndividuals)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}