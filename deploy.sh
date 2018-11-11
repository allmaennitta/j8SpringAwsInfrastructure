#!/usr/bin/env bash
VERSION=0.1.0
NAME=none
JAR=./$NAME-$VERSION-SNAPSHOT.jar
PROFILE=develop
TAG=v$VERSION
AWSURL=1234.dkr.ecr.eu-central-1.amazonaws.com

mvn clean install && \
cp Docker/Dockerfile target/ && \
$(aws ecr get-login --no-include-email --region eu-central-1 --profile $PROFILE) && \
docker build --no-cache --build-arg JAR_FILE=$JAR -t $NAME:$TAG -t $NAME:latest target/ && \
docker tag $NAME:$TAG $AWSURL/$NAME:$TAG && \
docker tag $NAME:latest $AWSURL/$NAME:latest && \
docker push $AWSURL/$NAME:$TAG && \
docker push $AWSURL/$NAME:latest && \
aws ecs update-service --service none --cluster none-cluster --profile $PROFILE --force-new-deployment
