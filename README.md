Scaling Mobile Code Offloading
==============================

Experiences and lessons learned regarding scaling a computational offloading deployment in the cloud.  The main contribution of this project is the extra front-end component that distributes the code offloading requests among the active servers.


How to cite
===========

Soon...


System overview
===========

![alt text](https://raw.githubusercontent.com/huberflores/ScalingMobileCodeOffloading/master/Scaling-mobile-code-offloading.png "Scaling Mobile Computational Offloading")



Installation
============

Project is mavenized for automatic compilation and deployment.


```xml
$ git clone https://github.com/huberflores/ScalingMobileCodeOffloading.git
````

```xml
$ mvn clean install
````

```xml
$ mvn org.apache.maven.plugins:maven-assembly-plugin:2.2-beta-2:assembly
````

Upload front-end to instance

```xml
$ scp -i /home/huber/.../HuberFlores/Ireland/pk-huber_flores.pem -r /home/huber/.../ScalingMobileCodeOffloading/Framework/Manager/CodeOffload/target/frontEnd-0.0.1-SNAPSHOT-jar-with-dependencies.jar ubuntu@ec2-xxx-xxx-xxx-xxx.eu-west-1.compute.amazonaws.com:/home/ubuntu/android-x86/
````

Connect to the instance

```xml
$ ssh -i /home/huber/.../HuberFlores/Ireland/pk-huber_flores.pem ubuntu@ec2-xxx-xxx-xxx-xxx.eu-west-1.compute.amazonaws.com
````

```xml
$ ubuntu@ec2$ nohup java -jar frontEnd-0.0.1-SNAPSHOT-jar-with-dependencies.jar &
````

