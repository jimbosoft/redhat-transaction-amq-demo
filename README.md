
# Program demonstrating the problems with ActiveMQ  
  
Connect to a ActiveMQ topic (multicast address) that is configured like this:  
```
   <address-setting match="app.test.multicast.address">  
       <expiry-address>expired.app.test.multicast.address</expiry-address>  
       <expiry-delay>600000</expiry-delay>  
       <redelivery-delay>2000</redelivery-delay>  
       <redelivery-delay-multiplier>2</redelivery-delay-multiplier>  
       <max-redelivery-delay>10000</max-redelivery-delay>  
       <max-delivery-attempts>-1</max-delivery-attempts>  
   </address-setting>  
```  
Run the program to create the durable queue.  
Stop the program.  
Send several messages to the topic with the first character being a number. When the first character is '1', a delivery failure will be triggered.
E.g. "1 This is a message"; "2 This is a message" etc
Start the program again.  
  
## Problems to be observed  
1.) **Messages arrive at the "redelivery-delay" interval set on the address above.**
Expected: Messages arrive as fast a possible as soon as processing is finished.

2.) **Messages arrive out of order**
Expected: The key feature of a queue is sequential delivery, else it can not be called a queue.

3.) **If a message fails it will be re-redelivered, but in the mean time it fetches the next message**.
Expected: While the message in front has not been successfully delivered, no other messages are processed. However this was my expectation and I accept that there can be other interpretations of re-delivery. Unfortunately this feature makes it unworkable for our "reliable delivery" requirement, where processing of data from the queue stops till it can be successfully delivered to the next service.

## Special Feature 
This software was written as stand alone program and all company references and dependencies have been removed.
