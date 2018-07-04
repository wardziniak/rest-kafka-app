# rest-kafka-app
Rest Application, that uses Kafka as a storage



Previously publishing data (insert, update) can be made via KafkaProducer, but there weren't simple way to retrieve data by Key.
Kafka Streams library introduce how easily retrieve data from Apache Kafka.
This project shows how it can be made.
More details can be found https://wardziniak.gitbook.io/apache-kafka/kafka-as-storage

How to start application:
sbt run

Application expose API to manage Person data.
Model: case class Person(id: Int, name: String, age: Int, email: String)

To update/add new Person following command can be used:
curl -X PUT -H 'Content-Type: application/json' localhost:9000/people/2 -d '{ "id": 2, "name": "John", "age": 38}'

Retrieve Person data:
curl -X GET -H 'Content-Type: application/json' localhost:9000/people/2


