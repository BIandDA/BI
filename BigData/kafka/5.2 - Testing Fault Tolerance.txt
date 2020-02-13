# This code is made for Unix-based systems such as Linux and Mac OSX
# For Windows use bin\windows\ instead of bin/, and change the script extension
# to .bat

# Send some messages to our replicated topic, then kill the producer
> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic my-replicated-topic
...
my test message 1
my test message 2
^C

# Read messages from topic
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic my-replicated-topic
...
my test message 1
my test message 2
^C

# Now kill Broker 1
> ps aux | grep server-1.properties
7564 ttys002    0:15.91 /System/Library/Frameworks/JavaVM.framework/Versions/1.8/Home/bin/java...
> kill -9 7564

# For Windows use:
> wmic process get processid,caption,commandline | find "java.exe" | find "server-1.properties"
java.exe    java  -Xmx1G -Xms1G -server -XX:+UseG1GC ... build\libs\kafka_2.10-0.10.1.0.jar"  kafka.Kafka config\server-1.properties    644
> taskkill /pid 644 /f

# Check which node is the leader for our topic now
> bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic my-replicated-topic

# Try reading the messages again
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic my-replicated-topic
...
my test message 1
my test message 2
^C
