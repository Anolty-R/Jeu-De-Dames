# 🎮 Jeu de Dames Console avec Algorithme Alpha-Beta

Bienvenue dans ce projet de **jeu de dames jouable en console**, intégrant un puissant **algorithme de recherche Alpha-Beta** pour déterminer le meilleur coup à jouer.

## ✨ Fonctionnalités

- ⭐ **Interface console intuitive** pour jouer aux dames
- ⚙️ **Algorithme Alpha-Beta** pour minimiser les coups inefficaces
- ✅ **Règles classiques des dames** avec une légère variation :
  - Une dame ne peut pas se placer où elle le souhaite après avoir capturé un pion adverse.
  - Elle est limitée à la case immédiatement après le pion capturé.
- 🤖 2 modes IA, une avec le premier mouvement possible et une autre avec un choix de mouvement aléatoire parmi toutes les possibilités.

## 🛠️ Installation

1. Clonez ce dépôt :
   ```sh
   git clone https://github.com/votre-utilisateur/nom-du-repo.git
   ```
2. Accédez au dossier du projet :
   ```sh
   cd nom-du-repo
   ```
3. Compilez et exécutez le jeu :
   ```sh
   python main.py  # Si écrit en Python
   # Ou
   g++ main.cpp -o dames && ./dames  # Si écrit en C++
   ```

## 🌟 Comment jouer ?

- **Déplacez vos pions** selon les règles classiques du jeu de dames.
- **Utilisez la dame** pour capturer des pièces adverses, en respectant la règle spéciale.
- **Le premier joueur sans coup légal perd la partie !**

## 💪 Contribution

Les contributions sont les bienvenues !

1. Forkez le projet
2. Créez une branche avec votre feature (`git checkout -b feature-nom`)
3. Soumettez une PR

