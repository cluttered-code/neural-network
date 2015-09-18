```
 _   _                      _   _   _      _                      _
| \ | |                    | | | \ | |    | |                    | |
|  \| | ___ _   _ _ __ __ _| | |  \| | ___| |___      _____  _ __| | __
| . ` |/ _ \ | | | '__/ _` | | | . ` |/ _ \ __\ \ /\ / / _ \| '__| |/ /
| |\  |  __/ |_| | | | (_| | | | |\  |  __/ |_ \ V  V / (_) | |  |   <
|_| \_|\___|\__,_|_|  \__,_|_| |_| \_|\___|\__| \_/\_/ \___/|_|  |_|\_\
```
[ ![Download](https://api.bintray.com/packages/cluttered-code/maven/neural-network/images/download.svg)](https://bintray.com/cluttered-code/maven/neural-network/_latestVersion)
[![Java](https://img.shields.io/badge/java-8-blue.svg)](http://docs.oracle.com/javase/8/docs/api/)
[![License](https://img.shields.io/badge/license-APLv2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

[![Build Status](https://travis-ci.org/cluttered-code/neural-network.svg?branch=master)](https://travis-ci.org/cluttered-code/neural-network)
[![Coverage Status](https://coveralls.io/repos/cluttered-code/neural-network/badge.svg?branch=master)](https://coveralls.io/r/cluttered-code/neural-network?branch=master)

[![Dependency Status](https://www.versioneye.com/user/projects/54e42c2ed1ec577c9700028b/badge.svg?style=flat)](https://www.versioneye.com/user/projects/54e42c2ed1ec577c9700028b)


Run Tests with code coverage: `./gradlew clean cleanTest jacocoRootReport`

Include in **gradle** project:
```
repositories {
    jcenter()
}

dependencies {
    compile 'com.clutteredcode:neural-network:1.0.1'
}
```

Include in **maven** project:
```
<repositories>
    <repository>
        <id>jcenter</id>
        <url>http://jcenter.bintray.com/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.clutteredcode</groupId>
        <artifactId>neural-network</artifactId>
        <version>1.0.1</version>
    </dependency>
</dependencies>
```

Code Example:

[XOR Network](src/test/integration/XORTest.java)
