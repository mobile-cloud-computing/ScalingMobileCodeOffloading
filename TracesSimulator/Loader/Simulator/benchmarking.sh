#
# author Huber Flores
#

# where $1 is the number of requests

for t in {1..15}
do
   java -jar MobileOffloadSimulator-0.0.1-SNAPSHOT-jar-with-dependencies.jar $1

   #Waiting time
   sleep 30s #change  s=seconds, m=minutes, h=hours, d=days
done


