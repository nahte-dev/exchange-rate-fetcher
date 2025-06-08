# Exchange Rate Fetcher
### Ethan Morris

My design approach follows a similar architecture to that of an 
enterprise Java backend application, which I personally find more organized
and structured for taking advantage of Spring and Gradle's dependency and 
package management.

I have kept the various API integration providers separate and abstracted to allow 
for scaling with additional APIs or functionality within those specific APIs.

I've also written my own scalable metrics which are quite basic in this 
iteration but would allow for more useful or in-depth data to be captured.

Given the nature of this application, I have not included any domain, JPA/entity
or database level implementations, and subsequently the metrics are only
runtime/non-volatile. 

This overall took quite a bit longer than the estimated 3 hours, I believe
spent closer to 7 on this over the course of two days. That time could've been
significantly reduced if I kept the overall design much simpler,
i.e., one controller, one service etc.