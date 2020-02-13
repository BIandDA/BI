# This code is made for Unix-based systems such as Linux and Mac OSX
# For Windows use bin\windows\ instead of bin/, and change the script extension
# to .bat

# Install the Java Development Kit (JDK)
# http://www.oracle.com/technetwork/java/javase/downloads/index.html

# Download latest version from https://kafka.apache.org/downloads
# Unpack latest version
> tar -xzf <kafka tarball>
> cd <new kafka directory>

# Start Zookeeper
> bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka Server
> bin/kafka-server-start.sh config/server.properties
