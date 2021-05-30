# urlShortner
A Url shortner application based on Angular and SpringBoot

#Pre Requisites
node.js: v14
npm
maven
Java: v11

#How Tos
The urlShortner-ui has the Angular code
The urlShortner-backend has the SpringBoot project

Pull the branch to any folder you desire.
In the UI folder, run npm install to install the required libraries to run the project.
Once done, you can run ng serve and open localhost:4200 to view the UI.

For the backend service, open a cmd prompt in urlShortner-backend and run 'mvn clean install -Dmaven.test.skip=true' to generate the war file.
If you intend to deploy the war on tomcat, please do update the 'environment.ts' file with the appropriate baseURL.
Alternatively, you can open the project in your desired IDE and run it there. If you do so, you won't have to make any changes to the UI project except maybe for the port number.
Why 'mvn clean install -Dmaven.test.skip=true'? Because the test cases are failing. 
I tried my best to debug it in the time alloted but when working in a startup, you barely get time for yourself. Also we don't do ANY testing here. One of the reasons I want to leave. I do not like techincal compromises.
This is not an excuse. Just the reason. I am self taught and I am still working on this.
Thank you for your time and this wonderful opportunity. I do not expect much but if you can provide me a feedback of any sorts, it would highly appreciated.
The world is currently messed up in 2021 and everyone has their own problems. You taking time out for mine is the most kindest thing you could do for me.
