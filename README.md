# kafka streaming ecosystem demo app - Do read READ.ME and system arch. before looking into code.
Application for Kafka Streaming and Processing System 
## Problem Statement --
There are Users in a system. Each user has some basic details and user has also written some messages in the system.
User Basic Detail(e.g. id,Address, name) exist in system in form of a kafka msg on a topic 'user-basic'.
User Messages(e.g userId,message) also exist on system in a separate kafka msg on a different topic 'user-message'.
Problem is to read both msg of a Particular user from their respective Kafka Topic, combined the Msg in a single Detail msg and write it back as Kafka msg on different Topic.
Msg from topic can come in any order. You have to look both the kafka stream and once find same user-id on 2 different topic join them and write back to another topic.
Additionally system should also produce all the msg related to user.

## System Requirement -- Msg should not get lost once system reads a msg. 

## System Design  -- Please look for System Arch Diagram before reading Approach.

![Alt text](docs/images/system-arch.jpg?raw=true "Sysyem Architecture")


## Solution (Code is self explanatory, codes are better than stories ;) )
Demo app uses both Kafka client and Kafka Streaming Apis to solve this.
App is based on Spring Boot as Initializer.
 Topic is alreaady created in Kafka Cluster. Topic names - 'user-basic', 'user-message','user-detail' on a local cluster
 
 System Design :--
 1. Two  kafka producers writing msg to user-basic and user-message topics. 
 2. Kafka Streams Api are connecting to cluster and reading from both the Topics. A Straming Topolgy have been created for this. 
 3. Kafka Stream is also managing 2 Local Stream Store(wiz K-Table) in system for storing streams, in case msg are not in order. Can't wait for a particular msg to arrive.
    Each K-Table works as Stream Database locally(at very low level its a ksql DB). K-Table abstracts stream into queriable streams.
 4. Once a new msg arrive from any of Topic, both the K-table Join(like relational DB) Together on common User-id. 
    a) If common userId is found on 2 msg from different TOpic, A CustomJoiner is used for combining data and pushing back the resultant combined Stream to Kafka CLuster,      simultaniously freeing up K-Tables.
    b) In case if corresponding msg in other topic is not there in K-Table, then msg stream get push to one of the Corresponding K-Table.
    
5. Process step 1,4 run continuously, until there is external interrupt to system. Writing new topics, reading, storing, joining, combining and writing back the combine message to Kafka cluster. 

### To avoid Avro Registry, Custom Serde implementation provided for each model

### Leaving UT for a while.
