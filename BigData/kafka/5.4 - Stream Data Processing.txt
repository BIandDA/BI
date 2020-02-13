# This code is made for Unix-based systems such as Linux and Mac OSX
# For Windows use bin\windows\ instead of bin/, and change the script extension
# to .bat

# Run built-in WordCount algorithm on data stream
# This demo application behaves slightly differently because it is designed to
# operate on an infinite, unbounded stream of data instead of a bound file

# Create some data in a file
> echo -e "all streams lead to kafka\nhello kafka streams\njoin kafka summit" > file-input.txt

# OR on Windows
> echo all streams lead to kafka> file-input.txt
> echo hello kafka streams>> file-input.txt
> echo|set /p=join kafka summit>> file-input.txt

# Send data to new topic
> bin/kafka-topics.sh --create \
            --zookeeper localhost:2181 \
            --replication-factor 1 \
            --partitions 1 \
            --topic streams-file-input

# Send data to the topic
> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streams-file-input < file-input.txt

# Run WordCount application, this will terminate after a few seconds
> bin/kafka-run-class.sh org.apache.kafka.streams.examples.wordcount.WordCountDemo

# Inspect the output by reading from the topic where the data was written
# Use the built-in String and Long deserializers
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
            --topic streams-wordcount-output \
            --from-beginning \
            --formatter kafka.tools.DefaultMessageFormatter \
            --property print.key=true \
            --property print.value=true \
            --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
            --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

# Output should look like the following
all     1
lead    1
to      1
hello   1
streams 2
join    1
kafka   3
summit  1
