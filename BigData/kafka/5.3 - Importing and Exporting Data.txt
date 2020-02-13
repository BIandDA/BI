# This code is made for Unix-based systems such as Linux and Mac OSX
# For Windows use bin\windows\ instead of bin/, and change the script extension
# to .bat

# Create a simple text file to work with that has 2 lines
> echo -e "foo\nbar" > test.txt

# Setup connector in standalone mode
# pass in connection properties config
# then file connection config
# then file sync config (serialization)
# all configs here ship w/ Kafka and act as templates
> bin/connect-standalone.sh config/connect-standalone.properties config/connect-file-source.properties config/connect-file-sink.properties

# Once the above connector starts running, it will read test.txt
# and write to test.sink.txt
# We can see this by reading the contents of the file
> cat test.sink.txt
foo
bar

# To see the data in the consumer run the following
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic connect-test --from-beginning

# In a separate terminal window, add some lines to the file
echo "Another line" >> test.txt
