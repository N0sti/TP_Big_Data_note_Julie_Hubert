# Projet Hadoop TP Final

![Hadoop](https://img.shields.io/badge/Hadoop-3.3.6-blue)
![Docker](https://img.shields.io/badge/Docker-latest-brightgreen)
![License](https://img.shields.io/badge/license-MIT-green)

## 🎯 Description

Bienvenue dans le projet **Hadoop TP Final**. Ce projet vise à implémenter un système de recommandation d'amis basé sur un algorithme de filtrage collaboratif en utilisant des données massives traitées via Apache Hadoop. Le but est de simuler un mécanisme de recommandation que l'on retrouve sur des plateformes comme Facebook, LinkedIn ou Twitter, en utilisant les relations existantes entre utilisateurs pour générer des recommandations pertinentes. Ce TP permet de comprendre comment les systèmes de recommandation fonctionnent à grande échelle.

Le projet se compose principalement de la mise en œuvre d'un pipeline de traitement de données massives avec Hadoop. Il inclut des étapes pour importer des données dans HDFS, manipuler ces données avec des commandes Hadoop, et exécuter un algorithme de filtrage collaboratif.

## 🛠️ Prérequis

- Hadoop
- Docker
- Git
- Maven ou Gradle
- HDFS (Hadoop Distributed File System)

## 📦 Installation

```bash
# Cloner le repo
git clone https://github.com/votre-username/hadoop-tp-final.git
cd hadoop-tp-final

# Construire l'image Docker
cd ./deploy/
docker buildx build -t hadoop-tp-final-img .
```

## 🚀 Démarrage

```bash
# Lancer le conteneur
docker run -d --rm \
  -p 8088:8088 \
  -p 9870:9870 \
  -p 9864:9864 \
  -v "${PWD}/data:/tmp/data" \
  -v "${PWD}/jars:/tmp/jars" \
  --name conteneur_TP_Final \
  hadoop-tp-final-img
```

## 💾 Configuration HDFS

```bash
# Se connecter au conteneur
docker exec -it conteneur_TP_Final su - epfuser

# Créer la structure de dossiers
hdfs dfs -mkdir -p /input/relationships

# Vérifier la structure
hdfs dfs -ls /

# Importer les données
hdfs dfs -put /tmp/data/relationships/data.txt /input/relationships/
```

## 📁 Structure du Projet

```
hadoop-tp3/
├── .idea/
├── data/
│   └── relationships/
│       └── data.txt
├── .gitkeep
├── deploy/
├── jars/
│   └── p-collaborative-filtering-job-1/
│       └── src/
│           └── main/
│               └── java/
│                   └── org/
│                       └── epf/
│                           └── hadoop/
│                               └── colfil1/
│                                   ├── ColFilJob1.java
│                                   ├── Relationship.java
│                                   ├── RelationshipInputFormat.java
│                                   └── RelationshipRecordReader.java
│   └── p-collaborative-filtering-job-2/
│       └── src/
│           └── main/
│               └── java/
│                   └── org/
│                       └── epf/
│                           └── hadoop/
│                               └── colfil2/
│                                   └── ColFilJob2.java
│   └── p-collaborative-filtering-job-3/
│       └── src/
│           └── main/
│               └── java/
│                   └── org/
│                       └── epf/
│                           └── hadoop/
│                               └── colfil3/
│                                   └── ColFilJob3.java
├── .gitattributes
├── .gitignore
├── pom.xml
└── README.md

```

## 🔍 Points Clés

- `deploy/`: Configuration Docker
- `data/`: Données d'entrée
- `jars/`: Exécutables Java

## 📊 Ports Exposés

| Port  | Service                   |
|-------|---------------------------|
| 8088  | ResourceManager Web UI     |
| 9870  | NameNode Web UI            |
| 9864  | DataNode Web UI            |

## 🔗 Liens Utiles

- [Documentation Hadoop](https://hadoop.apache.org/docs/current/)
- [Documentation Docker](https://docs.docker.com/)