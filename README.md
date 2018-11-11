# Basic infrastructure of my AWS Spring Java Services 
This is a collection of best practices aggregated over several projects. It won't compile. It is more 
a footlocker to rummage around in.
 
## Architectural guidelines
### Hexagonal Tier Architecture
The central paradigm of [Hexagonal Architecture](http://tidyjava.com/hexagonal-architecture-powerful/)  is
that outer tiers shouldn't know about inner tiers. 

#### Outermost tier
* read and write to AWS-sqs-queues
* dispatch cloudwatch metrics
* send requests to wsdl-specified soap-services (at the moment there are no REST-endpoints yet)

#### Middle tier
* listen at some aws-sqs message queue)
* produce to some aws-sqs message queue)
* track info to a specific queue, be it for incoming or outgoing data or for 3rd-party requests
* intercept incoming message via AOP for tracking (ListenerAspect)
* calculate a result based on a rule engine
* unmarshal the json from the queue either as completely mapped pojo structure (traditional
Jackson mapping like done in most REST-services)
* unmarshal the json from the queue as JsonNode and deserialise only the needed parts later
  (only partly implemented)
  
#### Inner tier
All the inner process-magic happens in method "handle()" of the concrete instance of the 
AbstractHandler. 

### Spring Basics
* [Using Spring Configuration via Code](https://docs.spring.io/spring-javaconfig/docs/1.0.0.M4/reference/html/ch03.html) vs configuration via ComponentScan or xml-config
* [Startup-Logic using Spring Dependency Injection](https://www.baeldung.com/running-setup-logic-on-startup-in-spring)
* [Using Profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html) , see also sources referenced in this chapter, especially the below
* [Handling of properties, env-variables, profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)
* [Autoconfiguration](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-auto-configuration.html) in the sense that we switched it off completely, manually creating all needed beans


## Troubleshooting
###Why does my service stop right after starting correctly without any message?
By refactoring and re-wiring beans in a wrong way there might 
happen quite a nasty situation:

The sqs-Listener annotation works by registering annotated methods
in a map which are fed with messages as soon as the connected queue
has a message that can be fed.

Under above mentioned condition of wrongly wired beans it might happen that the 
thread-executor starts before messages have been registered which leads to the assumption:
"Nothing is connected. I can stop. Nothing to do." Here come the details. 

package: SPRING-MESSAGING

* AbstractMethodMessageHandler, detectHandlerMethods == calls ==>  

package: SPRING-CLOUD-AWS-MESSAGING  

* QueueMessageHandler => getMappingForMethod:   
  looking for sqs listener annotation and registering the method

/---/

package: SPRING-CLOUD-AWS-MESSAGING  

* SimpleMessageListenerContainer => initialize() == calls ==>
* AbstractMehtodListenerContainer => initialize => this.messageHandler.getHandlerMethods()  
  starts the Spring Threadpool Executor 

**Solution**:
The SimpleMessageListenerContainer must be implemented quite top-level at 
"ListenerProducerHandlerConfig" to assure that the method is registered BEFORE the
thread executor is started. 