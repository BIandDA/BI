# This code is made for Unix-based systems such as Linux and Mac OSX
# For Windows use bin\windows\ instead of bin/, and change the script extension
# to .bat

# Run built-in script to create new topic named "test" with 1 partition on 1 node
> bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

# See the topic
> bin/kafka-topics.sh --list --zookeeper localhost:2181
