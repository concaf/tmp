Title - Journey of a developer, developing on Kubernetes

It’s a new day, and you need to bootstrap a new project with shiny microservices and containers. And you need to stay as close to production as you can be, and production runs Kubernetes.

What you should do: write your code, build a container image, expose ports and environment variables

What you will need to do: learn new Kubernetes vocabulary like Pod, Deployment, Services, Jobs, ConfigMap, Secret and then start writing code

#### Introducing _Kedge_

Kedge lets you define your application in a very simple, quick and effortless manner, and deploys it to running Kubernetes clusters in one command.

You need to define only your application, nothing else, image, ports, environment variables, and that’s it.

Production is going to love it because they can set all they want in the application, like pod anti-affinity, active deadline seconds, and all that they want in the same spec

Instead of going through the spec, let’s simply dive into it and let it grow on you -

Note: for this blog, we will take an example of a very simple counter app

### Day 0 -

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

### Day 1 and onwards

Good morning! You now need to iterate on the application code. It's simply, it's easy, it's Kedge.

You make the changes to the code, use Kedge to build and push the new image and redeploy the application.

You do the following -

- `kedge build -i containscafeine/counter:v2 -p` - this pushed your new image
- Change the image in counter.yml to `containscafeine/counter:v2`
```yaml
name: counter

containers:
- image: containscafeine/counter:v2 # changed from v1

services:
- portMappings:
  - "5000"
```
- `kedge apply -f counter/counter.yml` - deploys changes to the already running application on your Kubernetes cluster

That's it. This is the iterative and declarative workflow with Kedge that you need to do.

Build, Push, Deploy -> Build, Push, Redeploy ...

Kedge builds on top of the Kubernetes spec. It does not take anything away from the end user, there are no abstractions or reductions from the Kubernetes spec. So after a few days, if you wish to add Kubernetes objects like secrets, configMaps, initContainers, jobs, etc, you can do that in your Kedge file itself.



