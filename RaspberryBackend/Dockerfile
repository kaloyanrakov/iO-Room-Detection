# Use an ARM base image for OpenJDK (ARM32 if your Pi OS is 32-bit, ARM64 if it’s 64-bit)
#FROM arm32v7/openjdk:17-jdk-slim  # For ARM32
FROM arm64v8/openjdk:17-jdk-slim
# Install dependencies
RUN apt-get update --fix-missing && apt-get install -y --no-install-recommends \
    wget \
    unzip \
    libopencv-dev \
    && rm -rf /var/lib/apt/lists/*

# Install Gradle (specific version 8.10)
RUN wget https://services.gradle.org/distributions/gradle-8.10-bin.zip -P /tmp && \
    unzip /tmp/gradle-8.10-bin.zip -d /opt && \
    rm /tmp/gradle-8.10-bin.zip && \
    ln -s /opt/gradle-8.10/bin/gradle /usr/bin/gradle

# Set up environment variables
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-armhf
ENV GRADLE_HOME=/opt/gradle-8.10
ENV PATH=$PATH:$GRADLE_HOME/bin

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY . /app

# Run Gradle build to prepare the project (use --no-daemon to prevent issues in Docker)
RUN gradle build --no-daemon

# Command to run your Java application
CMD ["gradle", "run"]