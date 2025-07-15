# 🚀 Java Maven CI/CD Pipeline with Jenkins, Docker, and Auto Versioning

This project demonstrates an end-to-end CI/CD pipeline for a Java-based Maven application, powered by **Jenkins**, **Docker**, and **GitHub integrations**. Designed as a **capstone DevOps project**, it focuses on building real-world pipeline automation — including **multi-branch workflows**, **semantic versioning**, **Git auto-commit**, and **Dockerized deployment**.

---

---

## 🛠️ Tech Stack

| Tool/Technology    | Purpose                                  |
|--------------------|------------------------------------------|
| Java 17 + Maven     | Build system and project packaging       |
| Jenkins (LTS)       | CI/CD orchestration                     |
| Docker              | Containerization and image deployment   |
| GitHub              | Source control and webhook triggering   |
| Groovy + Shell      | Pipeline scripting                      |
| Maven Plugins       | Auto-versioning (`build-helper`, `versions`) |

---

## 📌 Project Overview

This project implements:

- **Semantic auto-versioning** for Java/Maven project  
- **Push-triggered CI builds** via GitHub Webhooks  
- **Automated JAR packaging and Docker image creation**  
- **Version-tagged image pushes to DockerHub**  
- **Git auto-commit of version bumps to `main` branch**  
- **Modular Groovy script for reusable pipeline logic**

---

## 🖼️ Architecture Diagram

```plaintext
Developer Pushes Code ──► GitHub ──► Webhook Trigger
                                          │
                                          ▼
                                    Jenkins Job Trigger
                                          │
         ┌────────────────────────────────┼────────────────────────────────┐
         ▼                                ▼                                ▼
  [Version Increment]             [Maven Build: JAR]              [Docker Build & Push]
         ▼                                ▼                                ▼
[Auto Commit Version to Git]     [Store JAR in Docker]       [Push Image to DockerHub]
```

---

## 🧩 Pipeline Overview (Jenkinsfile)

**Pipeline stages (main branch only):**

1. **Init** — Load shared Groovy logic  
2. **Increment Version** — Uses Maven plugins to bump version  
3. **Build** — Runs `mvn clean package`  
4. **Image** — Builds Docker image and tags it with version  
5. **Deploy** — Placeholder for K8s/Cloud deploy  
6. **Commit Version** — Auto-commits updated `pom.xml` to `main`  

> 💡 Only the `main` branch runs the full pipeline with commit + image publishing.

---

## 🌳 Multi-Branch Strategy

### ✅ `main` Branch
- Runs full pipeline (version bump, build, Docker push, Git commit)

### 🧪 Other Branches (`jenkinjobs`, `feature/*`)
- CI validation only
- Skips versioning, Docker push, and Git commit

**Controlled via Jenkins DSL:**

```groovy
when {
  expression { env.BRANCH_NAME == 'main' }
}
```

---

## 🔧 Jenkins Configuration

Run Jenkins in Docker:

```bash
docker run -d -p 8080:8080 -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  jenkins/jenkins:lts
```

### Notes:
- Docker socket allows Jenkins to build Docker images from inside the container.
- Jenkins was configured with root permissions for Docker access.

---

## 📜 Groovy Shared Library (`script.groovy`)

Used for clean separation of logic.

### 🔹 `incr()`

- Parses version from `pom.xml`
- Increments patch version
- Commits updated version
- Sets:
  ```groovy
  IMAGE_NAME = "$version-$BUILD_NUMBER"
  ```

### 🔹 `buildjar()`
- Runs:
  ```bash
  mvn clean package
  ```

### 🔹 `buildimage()`
- Builds Docker image:
  ```bash
  docker build -t sahilf5/demoapp:$IMAGE_NAME .
  docker push sahilf5/demoapp:$IMAGE_NAME
  ```

### 🔹 `cred()`
- Configures Git identity for Jenkins
- Commits and pushes updated `pom.xml`

---

## 🐳 Dockerfile

```Dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/java-maven-app-*.jar app.jar
CMD ["java", "-jar", "app.jar"]
```

- Uses wildcard to support Maven-built JARs
- Lightweight base image

---

## 🔢 Versioning Logic

Handled in Groovy via Maven:

```bash
mvn build-helper:parse-version versions:set \
  -DnewVersion=${parsedVersion.majorVersion}.${parsedVersion.minorVersion}.${parsedVersion.nextIncrementalVersion} \
  versions:commit
```

### Example:

| Step               | Value              |
|--------------------|--------------------|
| Current Version     | `1.1.4-SNAPSHOT`   |
| Bumped Version      | `1.1.5`            |
| Jenkins Build #     | `42`               |
| Docker Tag          | `1.1.5-42`         |

---

## 🔁 Webhook Automation

- Configured GitHub webhook to:
  ```
  http://<jenkins-url>/github-webhook/
  ```

- Triggers pipeline on push to any branch
- Multi-branch Jenkins project auto-selects logic

---

## ☁️ Local & Cloud Setup

### Prerequisites:

```bash
# Install Docker
sudo apt update
sudo apt install -y docker.io
```

### Run Jenkins:

```bash
docker run -d -p 8080:8080 -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  jenkins/jenkins:lts
```

### Inside Jenkins container (optional):

```bash
chmod 666 /var/run/docker.sock
```

---

## 🧾 Directory Structure

```
.
├── Dockerfile
├── Jenkinsfile
├── script.groovy
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── com/example/
                └── App.java
```

---
---

## 📸 Screenshots

<p align="center">


  <img src="https://github.com/user-attachments/assets/b24404dc-263b-4f5a-9c47-a4ad0acd4abc" width="70%" alt="Screenshot 2" />
  <br/><br/>
  <img src="https://github.com/user-attachments/assets/1c9c5339-99b7-4fc8-a28a-16b5c406082f" width="100%" alt="Screenshot 3" />
  <br/><br/>
  <img src="https://github.com/user-attachments/assets/984aae46-373f-4e0d-8d3b-ba296c78b39f" width="100%" alt="Screenshot 4" />
  <br/><br/>
  <img src="https://github.com/user-attachments/assets/27a8665b-7ffc-4130-b24f-bba8881de591" width="90%" alt="Screenshot 5" />
  <br/><br/>
  <img src="https://github.com/user-attachments/assets/d6a10d1c-4cda-4592-bf27-e1be82ed0cc7" width="100%" alt="Screenshot 6" />
  <br/><br/>
  <img src="https://github.com/user-attachments/assets/817327d5-6aed-4923-ae8c-b2b1407ccba5" width="100%" alt="Screenshot 7" />
  <br/><br/>
  <img src="https://github.com/user-attachments/assets/af4a3852-e409-48ef-9be5-448ba393fad9" width="100%" alt="Screenshot 8" />
  <br/><br/>
  <img src="https://github.com/user-attachments/assets/a1f215ad-b2c5-4ba9-b520-aa9fb149b5bf" width="100%" alt="Screenshot 9" />
  <br/><br/>
  <img src="https://github.com/user-attachments/assets/f85e268b-cd4c-41d9-9d0e-95feea813860" width="100%" alt="Screenshot 10" />
  <br/><br/>
  <img src="https://github.com/user-attachments/assets/41b83dc7-84ec-4670-be63-2551cb58b5d4" width="100%" alt="Screenshot 11" />
  <br/><br/>
  <img src="https://github.com/user-attachments/assets/8f267fc1-0911-4d3c-99c0-56123d9995e9" width="60%" alt="Screenshot 12" />
</p>

---




## ✍️ Author & Acknowledgements

This project was created by **Sahil Bharne** 

### Includes hands-on work with:
- Jenkins scripted pipelines  
- Docker build and push automation  
- Maven versioning plugins  
- Git/GitHub automation  
- Webhook-driven CI flow  

> 🙏 Thanks to open-source maintainers of Maven, Jenkins, Docker, and GitHub for tools and plugins used.
