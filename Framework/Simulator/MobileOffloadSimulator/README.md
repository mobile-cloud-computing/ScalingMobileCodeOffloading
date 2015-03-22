Installation
============

Project is mavenized for automatic compilation and deployment.


```xml
$ git clone https://github.com/huberflores/ScalingMobileCodeOffloading.git
````

```xml
$ cd /Framework/Simulator/MobileOffloadSimulator/
````

```xml
$ mvn clean install
````

```xml
$ cd /target/
````

Usage:

```xml
$ java -jar Simulator-0.0.1-SNAPSHOT.jar 
Missing parameters
param 1: experimental time (in minutes), param 2: interarrival time (in seconds)
````

```xml
Generates load for 5 minutes and each user arrives with an interarrival rate of 0.1
$ java -jar Simulator-0.0.1-SNAPSHOT.jar 5 0.1

Total time of the request: 1635.0
Total time of the request: 1699.0
Total time of the request: 1729.0
Total time of the request: 1797.0
Total time of the request: 15121.0
Total time of the request: 15160.0
Total time of the request: 15101.0
Total time of the request: 15099.0
Total time of the request: 15102.0
Total time of the request: 15101.0
.
.
.
Total time of the request: 15110.0
Total time of the request: 15107.0
Total time of the request: 15106.0
````

