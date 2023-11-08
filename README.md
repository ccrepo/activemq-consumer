# Project: Activemq-Consumer

Project Activemq-Consumer is an example simple client to consume messages from ActiveMQ (JMS)

## Usage

1. Pre-requisites:

    Software:
      
    ```text
    Linux (Ubuntu 22.04.2 LTS (Jammy Jellyfish)).
    Open JDK version 19.0.2.
    Active MQ 5.18.2
    ```
        
2. Build:

    Navigate to project home directory and execute the following commands

    ```bash
    cd $projectDir
    ./gradlew clean
    ./gradlew build
    ```
    
3. Helper Script:

    There are helper scripts in the $projectDir/bin directory
    
    ```text
    $projectDir/bin/c: compile clean, build and generate javadoc.
    ```

    ```text
    $projectDir/bin/r: example run command.
    ```

