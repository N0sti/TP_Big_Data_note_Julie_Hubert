# Projet Hadoop TP Final

![Hadoop](https://img.shields.io/badge/Hadoop-3.3.6-blue)
![Docker](https://img.shields.io/badge/Docker-latest-brightgreen)

## 🎯 Description

Bienvenue dans le projet **Hadoop TP Final**. Ce projet vise à implémenter un système de recommandation d'amis basé sur un algorithme de filtrage collaboratif en utilisant des données massives traitées via Apache Hadoop. Le but est de simuler un mécanisme de recommandation que l'on retrouve sur des plateformes comme Facebook, LinkedIn ou Twitter, en utilisant les relations existantes entre utilisateurs pour générer des recommandations pertinentes. Ce TP permet de comprendre comment les systèmes de recommandation fonctionnent à grande échelle.

Le projet se compose principalement de la mise en œuvre d'un pipeline de traitement de données massives avec Hadoop. Il inclut des étapes pour importer des données dans HDFS, manipuler ces données avec des commandes Hadoop, et exécuter un algorithme de filtrage collaboratif.

## 🛠️ Prérequis

- Hadoop
- Docker
- Git
- Maven ou Gradle
- HDFS (Hadoop Distributed File System)


## 📦 Installation et Configuration

### 1. Clonage du Projet
```bash
# Cloner le repo
git clone https://github.com/votre-username/hadoop-tp-final.git
cd hadoop-tp-final
```

### 2. Construction de l'Image Docker
```bash
# Naviguer vers le dossier deploy
cd ./deploy/

# Construire l'image Docker
docker buildx build -t hadoop-tp-final-img .
```

### 3. Démarrage du Conteneur
```bash
# Lancer le conteneur avec les volumes montés
docker run -d --rm \
  -p 8088:8088 \
  -p 9870:9870 \
  -p 9864:9864 \
  -v "C:\Julie\MIN-5A\3- Outil big data\TP_final\hadoop-tp3\data:/tmp/data" \
  -v "C:\Julie\MIN-5A\3- Outil big data\TP_final\hadoop-tp3\jars:/tmp/jars" \
  --name conteneur_TP_Final \
  hadoop-tp-final-img
```

## 🔄 Configuration HDFS et Import des Données

### 1. Connexion au Conteneur
```bash
docker exec -it conteneur_TP_Final su - epfuser
```

### 2. Création et Vérification de la Structure HDFS
```bash 
# Créer le répertoire pour les relations
hdfs dfs -mkdir -p /input/relationships

# Vérifier la structure HDFS
hdfs dfs -ls /
hadoop fs -ls /input/relationships
```
### 3. Import des Données dans HDFS
```bash
# Copier les données vers HDFS
hadoop fs -put /tmp/data/relationships/data.txt /input/relationships/
hadoop fs -put /tmp/data/relationships/testdata.txt /input/relationships/  
```

### 4. Vérification des Données Sources
```bash
# Vérifier la présence du fichier de données
ls /tmp/data/relationships/
# Résultat attendu :
# data.txt
```

## 📦 Compilation et Déploiement

### 1. Compilation du Projet
```bash
# Quitter la session epfuser
exit

# Compiler le projet avec Maven
mvn clean install
```

### 2. Déploiement dans le Conteneur
```bash
# Copier le JAR dans le conteneur
docker cp "C:\Julie\MIN-5A\3- Outil big data\TP_final\hadoop-tp3\p-collaborative-filtering-job-1\target\hadoop-tp3-collaborativeFiltering-job1-1.0.jar" conteneur_TP_Final:/tmp/jars/

# Se reconnecter au conteneur et vérifier le déploiement
docker exec -it conteneur_TP_Final su - epfuser
cd /tmp/jars
ls -l

# Résultat attendu :
# total 8
# -rwxr-xr-x 1 root root 5916 Jan 12 18:34 hadoop-tp3-collaborativeFiltering-job1-1.0.jar
```

### 3. Exécution du Job MapReduce
```bash
# Lancer le job MapReduce
hadoop jar /tmp/jars/hadoop-tp3-collaborativeFiltering-job1-1.0.jar org.epf.hadoop.colfil1.ColFilJob1 /input/relationships/data.txt /output/job1

```
## Procédure pour tester le code
```bash
#supprimer l'ancien jar du conteneur
docker exec -it conteneur_TP_Final su - epfuser
cd /tmp/jars
ls -l
#verifier si il y a qqchose
rm rm hadoop-tp3-collaborativeFiltering-job1-1.0.jar
rm rm hadoop-tp3-collaborativeFiltering-job2-1.0.jar
ls -l
exit
#recompiler le projet pour avoir un nouveau jar
mvn clean install
#remettre le nouveau jar dans le conteneur
docker cp "C:\Julie\MIN-5A\3- Outil big data\TP_final\hadoop-tp3\p-collaborative-filtering-job-1\target\hadoop-tp3-collaborativeFiltering-job1-1.0.jar" conteneur_TP_Final:/tmp/jars/
docker cp "C:\Julie\MIN-5A\3- Outil big data\TP_final\hadoop-tp3\p-collaborative-filtering-job-2\target\hadoop-tp3-collaborativeFiltering-job2-1.0.jar" conteneur_TP_Final:/tmp/jars/
docker exec -it conteneur_TP_Final su - epfuser
#verifier si le nouveau jar est bien la
cd /tmp/jars
ls -l
#suprimer l'ancien output
hdfs dfs -rm -r /output/job1
hdfs dfs -rm -r /output/job2
#relancer le job
hadoop jar /tmp/jars/hadoop-tp3-collaborativeFiltering-job1-1.0.jar org.epf.hadoop.colfil1.ColFilJob1 /input/relationships/data.txt /output/job1
hadoop jar /tmp/jars/hadoop-tp3-collaborativeFiltering-job2-1.0.jar org.epf.hadoop.colfil1.ColFilJob2 /output/job1 /output/job2
#lancer les tests
hadoop jar /tmp/jars/hadoop-tp3-collaborativeFiltering-job1-1.0.jar org.epf.hadoop.colfil1.ColFilJob1 /input/relationships/testdata.txt /output/job1test
hadoop jar /tmp/jars/hadoop-tp3-collaborativeFiltering-job2-1.0.jar org.epf.hadoop.colfil1.ColFilJob2 /output/job1test /output/job2test
#verifier le resultat
hdfs dfs -cat /output/job1/part-r-00000
hdfs dfs -ls /output/job1
hdfs dfs -cat /output/job2/part-r-00000
hdfs dfs -ls /output/job2
#afficher les resultats des tests
hdfs dfs -cat /output/job1test/part-r-00000
hdfs dfs -cat /output/job1test/part-r-00001
hdfs dfs -cat /output/job2test/part-r-00000
hdfs dfs -cat /output/job2test/part-r-00001
```
### Extraire les Résultats et les afficher dans le PC en local
#### Dans le conteneur
```bash
mkdir -p /home/epfuser/relationships/job1
hdfs dfs -get /output/job1 /home/epfuser/relationships/job1
ls /home/epfuser/relationships/job1  # Pour vérifier que les fichiers ont été copiés
exit  # Pour sortir du conteneur
```
#### Dans le PC local
```bash
mkdir -p "C:\Julie\MIN-5A\3- Outil big data\TP_final\hadoop-tp3\data\relationships\job1"
docker cp "conteneur_TP_Final:/home/epfuser/relationships/job1" "C:\Julie\MIN-5A\3- Outil big data\TP_final\hadoop-tp3\data\relationships\job1"
```
regler le pb de connexion au resource manager lors de l'execution du job 2, et permetre d'acceder a l'interface graphique sur http://localhost:8088
```bash
docker exec -it conteneur_TP_Final su - epfuser
export YARN_RESOURCEMANAGER_USER=epfuser
export YARN_NODEMANAGER_USER=epfuser
start-yarn.sh
jps
```
## 📁 Structure du Projet

```
hadoop-tp3/
├── .idea/
├── data/
│   └── relationships/
│       └── data.txt
│       └── testdata.txt
├── .gitkeep
├── schema_des_donnees_test.png
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