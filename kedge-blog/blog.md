Title - Journey of a developer, developing on Kubernetes

Write a use case story on how Kedge is used by developer who is developing microservice running on Kubernetes
How does the developer start the project?
How does the developer maintain the microservice, how does the developer maintain the kedge files?

Day 0 story - kedge build - kedge init - kedge create
Day 1 - Day 10 -- - add microservices - iterative application development - shortcuts - kedge apply
Growing out of Kedge
What is Kedge not for?
Kedge vs ?

---

It’s a new day, and you just got this exciting opportunity to bootstrap this new project that your team will be working on. You need to do it right, this time. So you lay out all the components of your new application-would-be, and you create this shiny microservices architecture based components of your application. You need your frontend, and an API server serving behind it.

Gone are the days where you only used to worry about writing your own application code, now you need to own your code from development to staging to production. You own what you write. One very important key task that you identify is that for this to happen and for random easter egg errors not to occur, you need to keep the environment both, same and sane, throughout all the stages.

Sigh, in all of your previous projects, you have been used to either -
Run your code locally on your bare machine and let the testing against other components happen in the CI
Run your entire application stacks using Docker Compose, do a simple docker-compose up and let Docker take care of the rest
Force push to master and pray everything works fine

But none of this is going to cut it anymore, because you need to run production on your machine, right here right now. But that scares you, because production runs this koob… kweber… Kubernetes thing that you have no idea about. All you want is to write your code, maybe build a container image, expose some ports and environment variables, and be done for your application.

So you search on Google, “How to develop on Kubernetes locally” and you come to this blog, and “Hi, nice to meet you!”

What if I give you something that -

Let’s you define your application in such a simple, such a quick, such an effortless manner, and deploys in development and production environment alike.
You are going to love it because you define only and only your application, nothing else, image, ports, environment variables, and that’s it
Production is going to love it because they can set all they want in the application, like pod anti-affinity, active deadline seconds, and all that they want in the same spec
Solves world hunger

I give you Kedge!

Instead of going through the spec, let’s simply dive into it and let it grow on you -

Note: for this blog, we will take an example of a very simple counter app

Day 0 -

You have started the project, and it’s going awesome in your mind. This project is going to start small, just a couple of container images and a couple of ports exposed, and you should be good to go.

You write a simple Dockerfile that creates a container image with your code in it, all set and ready to fire.

Your Dockerfile looks something like -

```
FROM python:3.4

RUN pip install flask redis
COPY . /app
WORKDIR /app

EXPOSE 5000
CMD ["python", "app.py"]
```

You then tell Kedge to build your image and push it too -


```console
$ kedge build -i containscafeine/counter:v1 -p
INFO[0000] Building image 'containscafeine/counter:v1' from directory 'docker-counter' 
INFO[0000] Image 'containscafeine/counter:v1' from directory 'docker-counter' built successfully 
INFO[0000] Pushing image "containscafeine/counter:v1"   
The push refers to a repository [docker.io/containscafeine/counter]
...
redacted
...
INFO[0089] Successfully pushed image "containscafeine/counter:v1" 
```

And boom, now you have a container image ready.

Let's define that application now.

You only care about the declarative stuff that makes up your application, which in this case is, your container image and the ports that you want to expose, that's it.

You tell kedge to get you a file that defines exactly this much.

You run `kedge init` like the following -

`kedge init --name counter --image containscafeine/counter:v1 -p 5000`

This gives you a kedge file that looks like -

```yaml
name: counter

containers:
- image: containscafeine/counter:v1

services:
- portMappings:
  - "5000"
```

That was easy, and you could have written this down yourself anyway.

So let's do that for the redis container that your application requires.

```yaml
name: redis

containers:
- image: redis:3.0

services:
- portMappings:
  - 6379
```

Awesome! You're done! Yes, you are! You now have 2 files, counter.yml and redis.yml, and all you need to do now is to tell Kedge to go and deploy this on Kubernetes.

You run `kedge create` on the directory container both of the files -

```console
$ kedge create -f counter/
service "counter" created
deployment "counter" created
service "redis" created
deployment "redis" created
```

That's it! You application is now running on Kubernetes.

### Day 1

Good morning! You now need to iterate on the application code. It's simply, it's easy, it's Kedge.

You make the changes to the code, use Kedge to build and push the new image and redeploy the application.

You do the following -

- `kedge build -i containscafeine/counter:v2 -p` - this pushed your new image
- Change the image in counter.yml to `containscafeine/counter:v2`
- `kedge apply -f counter/counter.yml` - deploys changes to the already running application on your Kubernetes cluster

That's it. This is the iterative and declarative workflow with Kedge that you need to do.

Build, Push, Deploy -> Build, Push, Redeploy ...

---
