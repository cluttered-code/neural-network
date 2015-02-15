/*
 * Copyright 2015 David Clutter
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

import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static junit.framework.TestCase.*;
import static mockit.Deencapsulation.*;

/**
 * @author David Clutter
 */
@RunWith(JMockit.class)
public class GeneticPopulationTest {

    @Tested
    @SuppressWarnings("unused")
    private GeneticPopulation geneticPopulation;

    @Injectable
    @SuppressWarnings("unused")
    private final int population = 100;

    @Injectable
    @SuppressWarnings("unused")
    private final int elites = 1;

    @Injectable
    @SuppressWarnings("unused")
    private final double mutationRate = 0.15;

    @Test
    public void testConstructor() {
        final int obtainedPopulation = getField(geneticPopulation, "population");
        final int obtainedElites = getField(geneticPopulation, "elites");
        final double obtainedMutationRate = getField(geneticPopulation, "mutationRate");

        assertEquals(population, obtainedPopulation);
        assertEquals(elites, obtainedElites);
        assertEquals(mutationRate, obtainedMutationRate);
    }

    @Test
    public void testTrainNumberOfGenerations(@Mocked final List<Double> inputs) {
        final int generations = 8;

        new Expectations(geneticPopulation) {{
            invoke(geneticPopulation, "trainGeneration", inputs); times = generations;
        }};

        geneticPopulation.trainNumberOfGenerations(generations, inputs);
    }

    @Test
    public void trainUntilFitnessReached(@Mocked final List<Double> inputs,
                                         @Mocked final List<GeneticIndividual> rankings,
                                         @Mocked final GeneticIndividual individual) {
        final double goal = 200.0;
        final double lessThanGoal = goal - 1;
        final double moreThanGoal = goal + 1;
        setField(geneticPopulation, "rankings", rankings);

        new Expectations(geneticPopulation) {{
            rankings.get(0); times = 2; result = individual;
            individual.fitness(); returns(lessThanGoal, moreThanGoal);
            invoke(geneticPopulation, "trainGeneration", inputs); times = 1;
        }};

        geneticPopulation.trainUntilFitnessReached(goal, inputs);
    }

    @Test
    public void testTrainGeneration(@Mocked final List<Double> inputs) {
        new Expectations(geneticPopulation) {{
            invoke(geneticPopulation, "trainAndRankAllIndividuals", inputs); times = 1;
            invoke(geneticPopulation, "crossoverGeneration"); times = 1;
            invoke(geneticPopulation, "mutateGeneration"); times = 1;
        }};

        invoke(geneticPopulation, "trainGeneration", inputs);
    }

    @Test
    public void testTrainAndRankAllIndividuals(@Mocked final List<Double> inputs,
                                               @Mocked final GeneticIndividual individual,
                                               @Mocked final PriorityQueue<GeneticIndividual> rankingsHeap) {
        final List<GeneticIndividual> overrideGeneration = Arrays.asList(individual, individual,individual);
        setField(geneticPopulation, "generation", overrideGeneration);

        new Expectations(geneticPopulation) {{
            new PriorityQueue<>(population, GeneticPopulation.individualComparator); times = 1; result = rankingsHeap;
            individual.train(inputs); times = overrideGeneration.size();
            rankingsHeap.offer(individual); times = overrideGeneration.size();
            invoke(geneticPopulation, "populateSortedRankingsWithHeap", rankingsHeap); times = 1;
        }};

        invoke(geneticPopulation, "trainAndRankAllIndividuals", inputs);
    }

    @Test
    public void testPopulateSortedRankingsWithHeap(
            @Mocked final PriorityQueue<GeneticIndividual> rankingsHeap,
            @Mocked final List<GeneticIndividual> rankings,
            @Mocked final GeneticIndividual individual) {

        setField(geneticPopulation, "rankings", rankings);

        new Expectations() {{
            rankings.clear();
            rankingsHeap.isEmpty(); times = 2; returns(false, true);
            rankingsHeap.poll(); times = 1; result = individual;
            rankings.add(individual); times = 1;
        }};

        invoke(geneticPopulation, "populateSortedRankingsWithHeap", rankingsHeap);
    }

    @Test
    public void testCrossoverGeneration(@Mocked final List<GeneticIndividual> generation,
                                        @Mocked final GeneticIndividual individual) {
        final double totalFitness = 500.0;
        setField(geneticPopulation, "generation", generation);

        new Expectations(geneticPopulation) {{
            generation.subList(0, elites); result = generation;
            List<GeneticIndividual> nextGeneration = new LinkedList<>(generation); result = generation;
            invoke(geneticPopulation, "adjustedTotalFitness"); result = totalFitness;
            invoke(geneticPopulation, "selectAndCrossoverAPairOfIndividuals", totalFitness); result = individual;
            nextGeneration.add(individual);

        }};

        invoke(geneticPopulation, "crossoverGeneration");
    }

    @Test
    public void testTotalAdjustedFitness(@Mocked final GeneticIndividual individual) {
        final List<GeneticIndividual> rankings = Arrays.asList(individual, individual, individual);
        setField(geneticPopulation, "rankings", rankings);

        new Expectations(rankings) {{
            individual.fitness(); times = 4; returns(-2.0, 5.0, 3.0, -2.0);
        }};

        final double totalFitness = invoke(geneticPopulation, "adjustedTotalFitness");

        assertEquals(12.0, totalFitness);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSelectAndCrossoverAPairOfIndividuals(
            @Mocked final GeneticIndividual individual) {

        final double totalFitness = Math.PI;

        new Expectations(geneticPopulation) {{
            invoke(geneticPopulation, "selectIndividualUsingFitnessProportion", totalFitness); result = individual;
            individual.crossover(individual); result = individual;
        }};

        final GeneticIndividual result = invoke(geneticPopulation, "selectAndCrossoverAPairOfIndividuals", totalFitness);

        assertEquals(individual, result);
    }

    @Test
    public void testSelectIndividualUsingFitnessProportion(@Mocked final GeneticIndividual individual1,
                                                           @Mocked final GeneticIndividual individual2,
                                                           @Mocked final GeneticIndividual individual3,
                                                           @Mocked("random") final Math unused) {
        final double totalFitness = 10;
        final List<GeneticIndividual> rankings = Arrays.asList(individual1, individual2, individual3);
        setField(geneticPopulation, "rankings", rankings);

        new Expectations(geneticPopulation) {{
            Math.random(); result = 0.25;
            invoke(geneticPopulation, "fitnessOffset"); result = 1.5;
            individual1.fitness(); result = 3.9;
            individual2.fitness(); result = 2.5;
        }};

        final GeneticIndividual result = invoke(geneticPopulation, "selectIndividualUsingFitnessProportion", totalFitness);

        assertEquals(individual2, result);
    }

    @Test
    public void testSelectIndividualUsingFitnessProportion_EmptyList() {
        final List<GeneticIndividual> rankings = Collections.emptyList();
        setField(geneticPopulation, "rankings", rankings);

        new Expectations(geneticPopulation) {{
            invoke(geneticPopulation, "fitnessOffset");
        }};

        final GeneticIndividual result = invoke(geneticPopulation, "selectIndividualUsingFitnessProportion", 100.0);

        assertNull(result);
    }

    @Test
    public void testMutateGeneration(@Mocked final GeneticIndividual individual) {
        final List<GeneticIndividual> generation = Arrays.asList(individual, individual, individual);
        setField(geneticPopulation, "generation", generation);

        new Expectations(LinkedList.class) {{
            individual.mutate(mutationRate); times = generation.size() - 1;  result = individual;
        }};

        invoke(geneticPopulation, "mutateGeneration");

        final List<GeneticIndividual> nextGeneration = getField(geneticPopulation, "generation");

        assertNotSame(generation, nextGeneration);
        assertEquals(generation.size(), nextGeneration.size());
    }
}