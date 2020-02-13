# This code is made for Unix-based systems such as Linux and Mac OSX
# For Windows use bin\windows\ instead of bin/, and change the script extension
# to .bat

# Run the producer in terminal and enter some messages
> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
message 1
message 2
message 3

# In a new terminal window read the messages
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
message 1
message 2
message 3

# If you go side-by-side with the terminal windows you can type in the producer
# window and see the results appear in the consumer
