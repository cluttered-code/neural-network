```
 _   _                      _   _   _      _                      _
| \ | |                    | | | \ | |    | |                    | |
|  \| | ___ _   _ _ __ __ _| | |  \| | ___| |___      _____  _ __| | __
| . ` |/ _ \ | | | '__/ _` | | | . ` |/ _ \ __\ \ /\ / / _ \| '__| |/ /
| |\  |  __/ |_| | | | (_| | | | |\  |  __/ |_ \ V  V / (_) | |  |   <
|_| \_|\___|\__,_|_|  \__,_|_| |_| \_|\___|\__| \_/\_/ \___/|_|  |_|\_\
```
[![Java](https://img.shields.io/badge/java-8-blue.svg)](http://docs.oracle.com/javase/8/docs/api/)
[![License](https://img.shields.io/badge/license-APLv2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

[![Build Status](https://travis-ci.org/cluttered-code/neural-network.svg?branch=master)](https://travis-ci.org/cluttered-code/neural-network)
[![Coverage Status](https://coveralls.io/repos/cluttered-code/neural-network/badge.svg?branch=master)](https://coveralls.io/r/cluttered-code/neural-network?branch=master)

Include in maven project
```
<repositories>
    <repository>
        <id>cluttered-code</id>
        <url>http://dl.bintray.com/cluttered-code/maven</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.clutteredcode</groupId>
        <artifactId>neural-network</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

Include in gradle project
```
repositories {
    maven {
        url 'http://dl.bintray.com/cluttered-code/maven'
    }
}

dependencies {
    compile 'com.clutteredcode:neural-network:1.0.0'
}
```


Run Tests with code coverage: `./gradlew clean cleanTest jacocoRootReport`
