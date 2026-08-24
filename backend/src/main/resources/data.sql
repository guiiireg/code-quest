-- Insertion des rôles
INSERT INTO roles (id, name) VALUES
('role-user', 'ROLE_USER'),
('role-admin', 'ROLE_ADMIN')
ON CONFLICT (id) DO NOTHING;

-- Insertion de l'utilisateur Administrateur 'gui' (Mot de passe: password)
INSERT INTO users (id, username, email, password) VALUES
('user-gui-admin', 'gui', 'gui@codequest.dev', '$2a$10$LZ/agDfAw49HxZQW/boRquU9w9ZqnqemD.ERvXl7HCP3j0n8mTYd.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO user_roles (user_id, role_id) VALUES
('user-gui-admin', 'role-admin'),
('user-gui-admin', 'role-user')
ON CONFLICT (user_id, role_id) DO NOTHING;

-- Insertion des mondes (Régions du parcours Full-Stack)
INSERT INTO worlds (id, name, description) VALUES
('world-1', '📜 Région 1 — HTML5', 'Le point de départ fondamental du développement Web. Apprenez à structurer le web sémantique, créer des formulaires et maîtriser l’accessibilité.'),
('world-2', '♿ Région 2 — Accessibilité Web', 'Le Royaume de l’Inclusion. Maîtrisez les WCAG 2.2 AA, le HTML sémantique, la navigation au clavier, les lecteurs d’écran et WAI-ARIA.'),
('world-3', '🎨 Région 3 — CSS3', 'Le Domaine des Formes & des Couleurs. Maîtrisez Flexbox, Grid, les animations, le responsive design, les variables CSS et les fonctionnalités modernes.')
ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, description = EXCLUDED.description;

-- Insertion des quêtes pour Région 1, Région 2 et Région 3
INSERT INTO quests (id, title, description, xp_reward, difficulty, code_template, test_validation_regex, world_id, category, languages, concept, theory) VALUES
('html-1-1', '1.1 L''Échange des Realms (Client / Serveur)', 'Complétez la balise <span> ci-dessous en lui ajoutant les attributs suivants :
1. data-methode="GET"
2. data-statut="200"', 100, 'EASY', '<div id="requete-web">
  <!-- Complétez la balise span ci-dessous avec data-methode="GET" et data-statut="200" -->
  <span></span>
</div>', '(?s).*data-methode="GET".*data-statut="200".*', 'world-1', '1. Structure ancestrale', 'HTML5, HTTP', 'Client / Serveur', 'Le Web repose sur le modèle Client / Serveur. Le navigateur (Client) émet des requêtes HTTP (ex: GET pour demander une page) vers un serveur web. Le serveur traite la demande et renvoie une réponse HTTP contenant un code statut (200 OK indique que la ressource a été trouvée avec succès).'),
('html-1-2', '1.2 Le Décodeur du Navigateur (HTML & Interprétation)', 'Rédigez une balise paragraphe <p> fermée par </p> contenant exactement le texte "Bienvenue aventurier".', 100, 'EASY', '<!-- Rédigez votre paragraphe <p> ci-dessous -->
', '(?s).*<p\s*>Bienvenue aventurier</p\s*>.*', 'world-1', '1. Structure ancestrale', 'HTML5', 'Interprétation HTML', 'HTML (HyperText Markup Language) est un langage de balisage. Le navigateur décode le fichier ligne par ligne pour créer les éléments visuels. Toute balise ouvrante comme <p> doit posséder sa balise fermante </p> correspondante.'),
('html-1-3', '1.3 L''Arbre de Vie (Le DOM)', 'Créez l''arborescence DOM de base avec l''élément racine <html> englobant l''en-tête <head></head> puis le corps <body></body>.', 150, 'EASY', '<!-- Rédigez l''élément <html> contenant <head></head> et <body></body> -->
', '(?s).*<html\s*>.*<head\s*>\s*</head\s*>.*<body\s*>\s*</body\s*>.*</html\s*>.*', 'world-1', '1. Structure ancestrale', 'HTML5, DOM', 'Arbre DOM', 'Le DOM (Document Object Model) est la représentation sous forme d''arbre d''objets du document HTML. L''élément <html> en est le nœud racine, qui contient la branche <head> (métadonnées) et la branche <body> (éléments affichés).'),
('html-1-4', '1.4 Le Rituel de Fondation (Document HTML5)', 'Rédigez un document HTML5 complet débutant par <!DOCTYPE html>, suivi de <html>, de <head></head>, et de <body> contenant un titre <h1>Hello CodeQuest</h1>.', 200, 'EASY', '<!-- Créez le document <!DOCTYPE html> complet -->
', '(?s).*<!DOCTYPE\s+html\s*>.*<html\s*>.*<head\s*>\s*</head\s*>.*<body\s*>.*<h1\s*>Hello CodeQuest</h1>.*</body\s*>.*</html\s*>.*', 'world-1', '1. Structure ancestrale', 'HTML5', 'Document HTML & DOCTYPE', 'La déclaration <!DOCTYPE html> est obligatoire en haut de tout fichier HTML5 moderne. Elle ordonne au navigateur d''activer le mode de rendu conforme aux standards (standards mode).'),
('html-1-5', '1.5 Les Sceaux de Pouvoir (Attributs HTML)', '1. Ajoutez l''attribut lang="fr" sur la balise <html>.
2. Créez un <div> possédant l''identifiant id="mon-personnage" et la classe class="joueur-niveau-1".', 250, 'MEDIUM', '<!DOCTYPE html>
<html>
<head></head>
<body>
  <!-- Ajoutez ci-dessous la <div> avec id="mon-personnage" et class="joueur-niveau-1" -->
  
</body>
</html>', '(?s).*<html\s+lang="fr".*<div\s+.*(?:id="mon-personnage".*class="joueur-niveau-1"|class="joueur-niveau-1".*id="mon-personnage").*>.*</div>.*', 'world-1', '1. Structure ancestrale', 'HTML5', 'Attributs id & class', 'Les attributs ajoutent des propriétés aux éléments HTML. ''id'' assigne un identifiant unique à un élément dans la page. ''class'' associe un ou plusieurs styles CSS réutilisables. ''lang'' spécifie la langue du document.'),
('html-1-6', '1.6 Les Notes du Scribe (Commentaires HTML)', 'Ajoutez le commentaire <!-- Quete initiale accomplie --> immédiatement au-dessus de la balise <h1>CodeQuest</h1>.', 150, 'EASY', '<!-- Ajoutez le commentaire ci-dessous -->
<h1>CodeQuest</h1>', '(?s).*<!--\s*Quete initiale accomplie\s*-->.*<h1\s*>CodeQuest</h1>.*', 'world-1', '1. Structure ancestrale', 'HTML5', 'Commentaires HTML', 'Les commentaires se placent entre <!-- et -->. Ils ne sont pas affichés à l''écran par le navigateur et permettent aux développeurs d''annoter le code source.'),
('html-2-1', '2.1 Le Décret d''Encodage (charset)', 'Ajoutez la balise <meta charset="UTF-8"> à l''intérieur du bloc <head>.', 150, 'EASY', '<head>
  <!-- Spécifiez l''encodage UTF-8 ici -->
</head>', '(?s).*<meta\s+charset="UTF-8"\s*/?>.*', 'world-1', '2. Le Grimoire du <head>', 'HTML5', 'Encodage UTF-8', 'L''encodage UTF-8 permet au navigateur de lire et d''afficher correctement tous les caractères accentués français et symboles internationaux.'),
('html-2-2', '2.2 La Vision Adaptative (viewport)', 'Ajoutez la balise <meta name="viewport" content="width=device-width, initial-scale=1.0"> dans le <head>.', 200, 'MEDIUM', '<head>
  <!-- Configurez le viewport mobile ci-dessous -->
</head>', '(?s).*<meta\s+name="viewport"\s+content="width=device-width,\s*initial-scale=1\.0"\s*/?>.*', 'world-1', '2. Le Grimoire du <head>', 'HTML5', 'Viewport Mobile', 'La balise meta viewport est indispensable pour le Responsive Design. Elle adapte l''échelle d''affichage de la page à la largeur physique des smartphones.'),
('html-2-3', '2.3 Le Nom du Domaine (title)', 'Ajoutez le titre de document <title>CodeQuest - Région HTML5</title> dans la section <head>.', 100, 'EASY', '<head>
  <!-- Ajoutez le titre de la page ci-dessous -->
</head>', '(?s).*<title\s*>CodeQuest - Région HTML5</title\s*>.*', 'world-1', '2. Le Grimoire du <head>', 'HTML5', 'Titre du document', 'La balise <title> définit le texte affiché dans l''onglet du navigateur et dans les résultats des moteurs de recherche Google.'),
('html-2-4', '2.4 Le Résumé de la Légende (description)', 'Ajoutez la balise meta description avec la valeur content="Apprenez le HTML5 dans CodeQuest" au <head>.', 150, 'EASY', '<head>
  <!-- Rédigez la meta description ci-dessous -->
</head>', '(?s).*<meta\s+name="description"\s+content="Apprenez le HTML5 dans CodeQuest"\s*/?>.*', 'world-1', '2. Le Grimoire du <head>', 'HTML5', 'SEO Meta Description', 'La meta description fournit un extrait résumant la page qui est affiché sous le titre dans les résultats des moteurs de recherche.'),
('html-2-5', '2.5 Le Blason du Royaume (favicon)', 'Associez l''icône favicon.ico au document avec la balise <link rel="icon" href="favicon.ico">.', 150, 'EASY', '<head>
  <!-- Ajoutez le lien vers le favicon ci-dessous -->
</head>', '(?s).*<link\s+rel="icon"\s+href="favicon\.ico"\s*/?>.*', 'world-1', '2. Le Grimoire du <head>', 'HTML5', 'Favicon Icon Link', 'Le favicon est la petite image identifiante affichée à côté du titre dans l''onglet du navigateur et dans les favoris.'),
('html-2-6', '2.6 La Source Authentique (canonical)', 'Déclarez l''URL officielle canonique avec <link rel="canonical" href="https://codequest.dev">.', 200, 'MEDIUM', '<head>
  <!-- Déclarez le lien canonique ci-dessous -->
</head>', '(?s).*<link\s+rel="canonical"\s+href="https://codequest\.dev"\s*/?>.*', 'world-1', '2. Le Grimoire du <head>', 'HTML5', 'Lien Canonique', 'Le lien canonique rel="canonical" indique aux robots des moteurs de recherche l''adresse originale maîtresse pour éviter les pénalités de contenu dupliqué.'),
('html-2-7', '2.7 Le Sortilège de Partage (Open Graph)', 'Ajoutez la balise meta Open Graph <meta property="og:title" content="CodeQuest"> dans le <head>.', 250, 'MEDIUM', '<head>
  <!-- Ajoutez la meta Open Graph og:title ci-dessous -->
</head>', '(?s).*<meta\s+property="og:title"\s+content="CodeQuest"\s*/?>.*', 'world-1', '2. Le Grimoire du <head>', 'HTML5', 'Meta Open Graph', 'Le protocole Open Graph permet de personnaliser le titre, l''image et la description affichés lors du partage de la page sur les réseaux sociaux.'),
('html-3-1', '3.1 La Hiérarchie des Sceaux (h1 → h6)', 'Écrivez un titre principal <h1>Royaume</h1> suivi d''un sous-titre de niveau 2 <h2>Région HTML5</h2>.', 150, 'EASY', '<!-- Écrivez le h1 et le h2 ci-dessous -->
', '(?s).*<h1\s*>Royaume</h1\s*>.*<h2\s*>Région HTML5</h2\s*>.*', 'world-1', '3. Texte & hiérarchie', 'HTML5', 'Balises de Titre h1..h6', 'Les balises <h1> à <h6> hiérarchisent les titres par ordre d''importance. <h1> est le titre principal unique de la page.'),
('html-3-2', '3.2 Les Parchemins d''Écriture (p)', 'Rédigez un paragraphe <p>L''aventure commence ici.</p>.', 100, 'EASY', '<!-- Rédigez le paragraphe <p> ci-dessous -->
', '(?s).*<p\s*>L''aventure commence ici\.</p\s*>.*', 'world-1', '3. Texte & hiérarchie', 'HTML5', 'Paragraphes', 'La balise <p> structure les blocs de texte. Le navigateur insère des espaces verticaux autour de chaque paragraphe pour séparer la lecture.'),
('html-3-3', '3.3 La Pause du Scribe (br)', 'Placez un saut de ligne <br> entre "Ligne 1" et "Ligne 2".', 100, 'EASY', 'Ligne 1
Ligne 2', '(?s).*Ligne 1\s*<br\s*/?>\s*Ligne 2.*', 'world-1', '3. Texte & hiérarchie', 'HTML5', 'Saut de ligne', 'La balise auto-fermante <br> crée un saut de ligne immédiat au sein d''un même paragraphe sans créer de nouveau bloc.'),
('html-3-4', '3.4 L''Emphase Guerrière (strong / em)', 'Entourez le mot "Fort" avec <strong>Fort</strong> et le mot "Important" avec <em>Important</em>.', 150, 'EASY', '<p>Un guerrier Fort et Important.</p>', '(?s).*<strong>Fort</strong>.*<em>Important</em>.*', 'world-1', '3. Texte & hiérarchie', 'HTML5', 'Sémantique strong & em', '<strong> indique une importance majeure (affiché en gras). <em> indique une emphase ou intonation orale spécifique (affiché en italique).'),
('html-3-5', '3.5 L''Encre d''Or (mark)', 'Surlignez le mot "Trésor" avec la balise <mark>Trésor</mark>.', 150, 'EASY', '<p>Voici le Trésor sacré.</p>', '(?s).*<mark\s*>Trésor</mark\s*>.*', 'world-1', '3. Texte & hiérarchie', 'HTML5', 'Surlignage mark', 'La balise <mark> surligne du texte pour attirer l''attention sur sa pertinence dans le contexte actuel.'),
('html-3-6', '3.6 Les Paroles des Anciens (citations)', 'Créez la citation <blockquote cite="http://sage.dev">Le savoir est une force.</blockquote>.', 200, 'MEDIUM', '<!-- Rédigez le bloc blockquote ci-dessous -->
', '(?s).*<blockquote\s+cite="http://sage\.dev"\s*>Le savoir est une force\.</blockquote\s*>.*', 'world-1', '3. Texte & hiérarchie', 'HTML5', 'Citations blockquote', '<blockquote> formate un grand bloc de citation. L''attribut ''cite'' précise l''adresse web de la source originale.'),
('html-4-1', '4.1 L''Ordre des Inventaires (ul / ol / li)', 'Créez une liste non ordonnée <ul> contenant trois éléments <li> : Épée, Bouclier, Potion.', 150, 'EASY', '<!-- Rédigez la liste <ul> avec ses 3 éléments <li> ci-dessous -->
', '(?s).*<ul\s*>.*<li>Épée</li>.*<li>Bouclier</li>.*<li>Potion</li>.*</ul\s*>.*', 'world-1', '4. Listes', 'HTML5', 'Listes ul, ol & li', '<ul> génère une liste à puces. Chaque élément individuel est placé dans une balise <li>.'),
('html-4-2', '4.2 Le Glossaire des Runes (dl / dt / dd)', 'Créez une liste de définition <dl> contenant le terme <dt>HTML</dt> et sa définition <dd>HyperText Markup Language</dd>.', 200, 'MEDIUM', '<!-- Rédigez la liste de définition <dl> ci-dessous -->
', '(?s).*<dl\s*>.*<dt\s*>HTML</dt\s*>.*<dd\s*>HyperText Markup Language</dd\s*>.*</dl\s*>.*', 'world-1', '4. Listes', 'HTML5', 'Listes de définition dl', 'L''élément <dl> regroupe des paires de termes <dt> et de descriptions associées <dd> (parfait pour glossaires et dictionnaires).'),
('html-4-3', '4.3 Les Arborescences de Sac (imbrication)', 'Imbriquez une sous-liste <ul> contenant <li>Dague</li> à l''intérieur de l''élément <li>Armes...</li>.', 250, 'MEDIUM', '<ul>
  <li>Armes
    <!-- Insérez la sous-liste <ul> avec <li>Dague</li> ici -->
  </li>
</ul>', '(?s).*<ul\s*>.*<li\s*>.*<ul\s*>.*<li\s*>Dague</li>.*</ul\s*>.*</li>.*</ul\s*>.*', 'world-1', '4. Listes', 'HTML5', 'Listes imbriquées', 'Pour sous-structurer des menus ou sous-dossiers, une sous-liste entière doit être directement insérée à l''intérieur de l''élément <li> parent.'),
('html-5-1', '5.1 Les Portails de Téléportation (a)', 'Créez un lien hypertexte <a href="https://codequest.dev">Rejoindre la Guilde</a>.', 100, 'EASY', '<!-- Rédigez le lien <a> ci-dessous -->
', '(?s).*<a\s+href="https://codequest\.dev"\s*>Rejoindre la Guilde</a\s*>.*', 'world-1', '5. Liens & navigation', 'HTML5', 'Hyperliens balise <a>', 'La balise <a> (anchor) permet de naviguer vers d''autres pages web grâce à l''attribut href.'),
('html-5-2', '5.2 Les Coordonnées de Destination (href)', 'Ajoutez l''attribut href="/sanctuaire.html" au lien ci-dessous pour pointer vers la page interne.', 150, 'EASY', '<!-- Ajoutez l''attribut href sur la balise <a> ci-dessous -->
<a>Sanctuaire</a>', '(?s).*<a\s+href="/sanctuaire\.html"\s*>Sanctuaire</a\s*>.*', 'world-1', '5. Liens & navigation', 'HTML5', 'Attribut href', 'L''attribut href spécifie l''adresse URL ou le chemin de fichier de destination du lien.'),
('html-5-3', '5.3 Les Sentiers & Cartes (chemins relatifs/absolus)', 'Créez un lien avec le chemin relatif vers le dossier parent : <a href="../inventaire/cartes.html">Cartes</a>.', 200, 'MEDIUM', '<!-- Rédigez le lien relatif ci-dessous -->
', '(?s).*<a\s+href="\.\./inventaire/cartes\.html"\s*>Cartes</a\s*>.*', 'world-1', '5. Liens & navigation', 'HTML5', 'Chemins relatifs & absolus', 'Un chemin absolu comporte le domaine complet (https://...). Un chemin relatif se résout à partir du dossier courant. L''instruction ../ remonte d''un dossier.'),
('html-5-4', '5.4 Les Balises d''Ancrage (ancres)', 'Créez un lien d''ancrage vers l''identifiant local "#chapitre-2" avec <a href="#chapitre-2">Chapitre 2</a>.', 150, 'EASY', '<!-- Rédigez le lien d''ancre ci-dessous -->
', '(?s).*<a\s+href="#chapitre-2"\s*>Chapitre 2</a\s*>.*', 'world-1', '5. Liens & navigation', 'HTML5', 'Ancres intra-page', 'Les ancres débutent par le symbole ''#''. Au clic, le défilement saute directement vers l''élément de la page qui possède l''id correspondant.'),
('html-5-5', '5.5 Les Pigeons Messagers (mailto / tel)', 'Créez un lien de messagerie électronique <a href="mailto:magie@codequest.dev">Contacter le Mage</a>.', 150, 'EASY', '<!-- Rédigez le lien mailto ci-dessous -->
', '(?s).*<a\s+href="mailto:magie@codequest\.dev"\s*>Contacter le Mage</a\s*>.*', 'world-1', '5. Liens & navigation', 'HTML5', 'Protocole mailto & tel', 'Le préfixe mailto: ouvre le logiciel d''e-mail par défaut de l''utilisateur avec l''adresse du destinataire préremplie.'),
('html-5-6', '5.6 Le Passage Sécurisé (target / rel)', 'Ouvrez le lien dans un nouvel onglet avec target="_blank" et sécurisez-le avec rel="noopener noreferrer".', 250, 'MEDIUM', '<!-- Ajoutez target="_blank" et rel="noopener noreferrer" au lien ci-dessous -->
<a href="https://w3.org">W3C</a>', '(?s).*<a\s+.*target="_blank".*rel="noopener noreferrer".*>W3C</a\s*>.*', 'world-1', '5. Liens & navigation', 'HTML5', 'Sécurité target & rel', 'target="_blank" ouvre la cible dans un nouvel onglet. rel="noopener noreferrer" est une sécurité essentielle pour empêcher le site ouvert de contrôler la page d''origine.'),
('html-6-1', '6.1 La Relique Visuelle (img)', 'Insérez une image avec src="bouclier.png" et le texte alternatif alt="Bouclier d''Or".', 100, 'EASY', '<!-- Insérez l''image <img ...> ci-dessous -->
', '(?s).*<img\s+.*src="bouclier\.png".*alt="Bouclier d''Or".*/?>.*', 'world-1', '6. Médias', 'HTML5', 'Balise img & alt', 'La balise <img> insère une image. L''attribut alt transmet la description textuelle pour l''accessibilité et si l''image échoue à charger.'),
('html-6-2', '6.2 La Stèle Illustrée (figure / figcaption)', 'Encadrez l''image <img src="armure.png" alt="Armure"> dans un <figure> et ajoutez la légende <figcaption>Armure ancestrale</figcaption>.', 200, 'MEDIUM', '<figure>
  <!-- Insérez l''image et sa légende figcaption ici -->
</figure>', '(?s).*<figure\s*>.*<img\s+.*src="armure\.png".*/>.*<figcaption\s*>Armure ancestrale</figcaption\s*>.*</figure\s*>.*', 'world-1', '6. Médias', 'HTML5', 'Figure & Legende', 'L''élément <figure> groupe une illustration et sa légende <figcaption>.'),
('html-6-3', '6.3 L''Art Multi-Format (picture)', 'Utilisez le conteneur <picture> pour proposer la source WebP "banniere.webp" et l''image de secours "banniere.png".', 250, 'MEDIUM', '<picture>
  <!-- Proposez la source webp et l''image de secours png ci-dessous -->
</picture>', '(?s).*<picture\s*>.*<source\s+.*srcset="banniere\.webp".*>.*<img\s+.*src="banniere\.png".*/>.*</picture\s*>.*', 'world-1', '6. Médias', 'HTML5', 'Responsive Picture', '<picture> permet de servir différents formats d''images selon le support du navigateur.'),
('html-6-4', '6.4 L''Adaptation des Fresques (srcset / sizes)', 'Ajoutez l''attribut srcset="carte-small.png 500w, carte-large.png 1000w" à la balise <img>.', 250, 'HARD', '<!-- Ajoutez l''attribut srcset sur la balise img ci-dessous -->
<img src="carte.png" alt="Carte">', '(?s).*<img\s+.*srcset="carte-small\.png\s+500w,\s*carte-large\.png\s+1000w".*/?>.*', 'world-1', '6. Médias', 'HTML5', 'Attributs srcset & sizes', 'l''attribut srcset fournit une liste d''images adaptées aux différentes résolutions d''écrans.'),
('html-6-5', '6.5 Le Chant du Barde (audio)', 'Intégrez un lecteur audio pour le fichier "chant.mp3" muni de l''attribut controls.', 150, 'EASY', '<!-- Intégrez le lecteur audio avec l''attribut controls ci-dessous -->
', '(?s).*<audio\s+.*controls.*src="chant\.mp3".*></audio\s*>.*', 'world-1', '6. Médias', 'HTML5', 'Lecteur Audio HTML5', 'La balise <audio controls> intègre un lecteur audio natif avec boutons lecture/pause et barre de volume.'),
('html-6-6', '6.6 La Vision Éthérée (video)', 'Intégrez une vidéo pour "intro.mp4" avec controls, width="640" et height="360".', 200, 'MEDIUM', '<!-- Intégrez la balise video ci-dessous -->
', '(?s).*<video\s+.*controls.*src="intro\.mp4".*></video\s*>.*', 'world-1', '6. Médias', 'HTML5', 'Lecteur Vidéo HTML5', 'La balise <video> permet la lecture vidéo directe sans plugin externe.'),
('html-6-7', '6.7 Les Flux Alternatifs (source)', 'Proposez les deux sources "hymne.mp3" (type audio/mpeg) et "hymne.ogg" (type audio/ogg) dans un lecteur <audio controls>.', 200, 'MEDIUM', '<audio controls>
  <!-- Insérez les deux balises <source> ici -->
</audio>', '(?s).*<audio\s+.*controls.*>.*<source\s+src="hymne\.mp3".*>.*<source\s+src="hymne\.ogg".*>.*</audio\s*>.*', 'world-1', '6. Médias', 'HTML5', 'Formats Source', 'Les balises <source> fournissent des formats alternatifs que le navigateur choisit selon sa compatibilité.'),
('html-6-8', '6.8 Les Sous-Titres du Livre (track)', 'Ajoutez des sous-titres en français <track kind="subtitles" src="fr.vtt" srclang="fr"> dans la vidéo.', 250, 'HARD', '<video controls src="cinematique.mp4">
  <!-- Ajoutez la balise track ci-dessous -->
</video>', '(?s).*<track\s+.*kind="subtitles".*src="fr\.vtt".*srclang="fr".*/?>.*', 'world-1', '6. Médias', 'HTML5', 'Pistes & Sous-titres', '<track> ajoute des sous-titres ou légendes synchronisés au format VTT.'),
('html-6-9', '6.9 La Fenêtre Quantique (iframe)', 'Incorporez une page web externe avec <iframe src="https://maps.codequest.dev"></iframe>.', 200, 'MEDIUM', '<!-- Insérez l''iframe ci-dessous -->
', '(?s).*<iframe\s+src="https://maps\.codequest\.dev"\s*>\s*</iframe>.*', 'world-1', '6. Médias', 'HTML5', 'Intégration iFrame', '<iframe> embarque un second document HTML dans la page actuelle.'),
('html-7-1', '7.1 Le Sommet de la Cité (header)', 'Encadrez le titre <h1>Guide du Développeur</h1> dans la balise sémantique <header>.', 150, 'EASY', '<!-- Encadrez le h1 dans un <header> ci-dessous -->
<h1>Guide du Développeur</h1>', '(?s).*<header\s*>.*<h1>Guide du Développeur</h1>.*</header\s*>.*', 'world-1', '7. Sémantique HTML5', 'HTML5', 'En-tête sémantique', 'L''élément <header> regroupe les éléments d''introduction de la page.'),
('html-7-2', '7.2 La Boussole de Navire (nav)', 'Encapsulez le lien de navigation dans la balise sémantique <nav>.', 150, 'EASY', '<!-- Encadrez le lien ci-dessous dans un <nav> -->
<a href="/accueil">Accueil</a>', '(?s).*<nav\s*>.*<a\s+href="/accueil"\s*>Accueil</a>.*</nav\s*>.*', 'world-1', '7. Sémantique HTML5', 'HTML5', 'Navigation sémantique', '<nav> définit le bloc de navigation principale du site.'),
('html-7-3', '7.3 Le Cœur du Sanctuaire (main)', 'Encadrez le paragraphe dans la balise de contenu principal <main>.', 150, 'EASY', '<!-- Encadrez le paragraphe dans un <main> ci-dessous -->
<p>Contenu principal du royaume.</p>', '(?s).*<main\s*>.*<p\s*>Contenu principal du royaume\.</p\s*>.*</main\s*>.*', 'world-1', '7. Sémantique HTML5', 'HTML5', 'Contenu principal main', '<main> contient le sujet principal unique de la page.'),
('html-7-4', '7.4 Les Salles du Donjon (section)', 'Encadrez le titre dans la balise de section sémantique <section>.', 150, 'EASY', '<!-- Encadrez le h2 dans une <section> ci-dessous -->
<h2>Chapitre 1</h2>', '(?s).*<section\s*>.*<h2>Chapitre 1</h2>.*</section\s*>.*', 'world-1', '7. Sémantique HTML5', 'HTML5', 'Section thématique', '<section> découpe le document en blocs thématiques autonomes.'),
('html-7-5', '7.5 La Chronique Autonome (article)', 'Regroupez le titre dans un composant autonome <article>.', 150, 'EASY', '<!-- Encadrez le h2 dans un <article> ci-dessous -->
<h2>Annonce de la Guilde</h2>', '(?s).*<article\s*>.*<h2>Annonce de la Guilde</h2>.*</article\s*>.*', 'world-1', '7. Sémantique HTML5', 'HTML5', 'Article autonome', '<article> englobe un contenu réutilisable de manière indépendante.'),
('html-7-6', '7.6 Les Notes de Marge (aside)', 'Placez la remarque tangente dans la balise sémantique <aside>.', 150, 'EASY', '<!-- Encadrez le paragraphe dans un <aside> ci-dessous -->
<p>Astuce : gardez toujours vos potions prêtes.</p>', '(?s).*<aside\s*>.*<p\s*>Astuce.*</p\s*>.*</aside\s*>.*', 'world-1', '7. Sémantique HTML5', 'HTML5', 'Contenu tangentiel aside', '<aside> contient des informations complémentaires en rapport indirect.'),
('html-7-7', '7.7 Le Pied du Parchemin (footer)', 'Encadrez le copyright dans la balise de pied de page <footer>.', 150, 'EASY', '<!-- Encadrez le paragraphe dans un <footer> ci-dessous -->
<p>© 2026 CodeQuest</p>', '(?s).*<footer\s*>.*<p\s*>© 2026 CodeQuest</p\s*>.*</footer\s*>.*', 'world-1', '7. Sémantique HTML5', 'HTML5', 'Pied de page footer', '<footer> conclut la page avec les crédits et mentions de copyright.'),
('html-7-8', '7.8 L''Adresse de la Guilde (address)', 'Encadrez l''adresse de contact dans la balise sémantique <address>.', 200, 'MEDIUM', '<!-- Encadrez le contact dans une balise <address> ci-dessous -->
Contact : <a href="mailto:admin@codequest.dev">admin@codequest.dev</a>', '(?s).*<address\s*>.*<a\s+href="mailto:admin@codequest\.dev"\s*>admin@codequest\.dev</a>.*</address\s*>.*', 'world-1', '7. Sémantique HTML5', 'HTML5', 'Contact address', '<address> fournit les coordonnées de contact de l''auteur du document.'),
('html-7-9', '7.9 L''Horloge Magique (time)', 'Encadrez la date "9 août 2026" dans <time datetime="2026-08-09">.', 200, 'MEDIUM', '<!-- Encadrez la date dans <time datetime="2026-08-09"> ci-dessous -->
9 août 2026', '(?s).*<time\s+datetime="2026-08-09"\s*>9 août 2026</time\s*>.*', 'world-1', '7. Sémantique HTML5', 'HTML5', 'Horodatage time', '<time> fournit un horodatage lisible par les machines grâce au format ISO.'),
('html-8-1', '8.1 Le Formulaire d''Enrôlement (form)', 'Déclarez le formulaire <form action="/soumettre" method="POST"></form>.', 150, 'EASY', '<!-- Créez le formulaire <form ...> ci-dessous -->
', '(?s).*<form\s+.*action="/soumettre".*method="POST".*>\s*</form\s*>.*', 'world-1', '8. Formulaires', 'HTML5', 'Balise Formulaire', '<form> encadre les champs de collecte transmis au serveur.'),
('html-8-2', '8.2 L''Étiquette des Champs (label)', 'Reliez <label for="pseudo">Nom de héros</label> au champ <input id="pseudo">.', 150, 'EASY', '<!-- Créez le label for="pseudo" et l''input id="pseudo" ci-dessous -->
', '(?s).*<label\s+for="pseudo"\s*>Nom de héros</label\s*>.*<input\s+.*id="pseudo".*/?>.*', 'world-1', '8. Formulaires', 'HTML5', 'Liaison Label & Input', '<label for="..."> associe un libellé à son champ de saisie pour l''accessibilité.'),
('html-8-3', '8.3 Le Champ de Saisie (input)', 'Déclarez un champ de saisie <input type="text" name="hero_name">.', 100, 'EASY', '<!-- Déclarez l''input type="text" name="hero_name" ci-dessous -->
', '(?s).*<input\s+.*type="text".*name="hero_name".*/?>.*', 'world-1', '8. Formulaires', 'HTML5', 'Champs Input', '<input> crée un champ d''entrée de données.'),
('html-8-4', '8.4 Le Grand Parchemin de Texte (textarea)', 'Créez la zone de texte multi-lignes <textarea name="message" rows="4"></textarea>.', 150, 'EASY', '<!-- Déclarez le textarea ci-dessous -->
', '(?s).*<textarea\s+.*name="message".*rows="4".*></textarea\s*>.*', 'world-1', '8. Formulaires', 'HTML5', 'Zone multi-lignes', '<textarea> offre une zone de saisie texte sur plusieurs lignes.'),
('html-8-5', '8.5 Le Choix des Sorts (select / option)', 'Créez une liste déroulante <select name="classe"> contenant l''option <option value="guerrier">Guerrier</option>.', 200, 'MEDIUM', '<select name="classe">
  <!-- Ajoutez l''option guerrier ci-dessous -->
</select>', '(?s).*<select\s+.*name="classe".*>.*<option\s+value="guerrier"\s*>Guerrier</option\s*>.*</select\s*>.*', 'world-1', '8. Formulaires', 'HTML5', 'Menu déroulant Select', '<select> génère un menu déroulant d''options de sélection.'),
('html-8-6', '8.6 Le Déclencheur d''Action (button)', 'Ajoutez le bouton de soumission <button type="submit">Valider la quête</button>.', 100, 'EASY', '<!-- Rédigez le bouton submit ci-dessous -->
', '(?s).*<button\s+type="submit"\s*>Valider la quête</button\s*>.*', 'world-1', '8. Formulaires', 'HTML5', 'Bouton de soumission', '<button type="submit"> soumet les données du formulaire.'),
('html-8-7', '8.7 Le Groupement d''Épreuves (fieldset / legend)', 'Regroupez des champs dans un <fieldset> titré par <legend>Identité</legend>.', 200, 'MEDIUM', '<fieldset>
  <!-- Ajoutez le legend Identité ici -->
</fieldset>', '(?s).*<fieldset\s*>.*<legend\s*>Identité</legend\s*>.*</fieldset\s*>.*', 'world-1', '8. Formulaires', 'HTML5', 'Fieldset & Legend', '<fieldset> et <legend> regroupent visuellement et sémantiquement des champs.'),
('html-8-8', '8.8 La Suggestion de Runes (datalist)', 'Reliez un input list="sorts" à une <datalist id="sorts"> contenant l''option <option value="Boule de feu">.', 250, 'HARD', '<input list="sorts">
<!-- Créez la datalist id="sorts" ci-dessous -->
', '(?s).*<input\s+.*list="sorts".*/?>.*<datalist\s+id="sorts"\s*>.*<option\s+value="Boule de feu"\s*/?>.*</datalist\s*>.*', 'world-1', '8. Formulaires', 'HTML5', 'Autocomplétion Datalist', '<datalist> propose des suggestions d''autocomplétion à l''utilisateur.'),
('html-8-9', '8.9 Les Gardiens de Saisie (validation native)', 'Rendez le champ email obligatoire en lui ajoutant l''attribut required.', 150, 'EASY', '<!-- Ajoutez l''attribut required à l''input ci-dessous -->
<input type="email" name="user_email">', '(?s).*<input\s+.*type="email".*required.*/?>.*', 'world-1', '8. Formulaires', 'HTML5', 'Validation HTML Native', '''required'' impose la saisie d''une valeur avant soumission.'),
('html-8-10', '8.10 Les Runes d''Attributs (attributs de formulaire)', 'Appliquez placeholder="Entrez votre nom" et autocomplete="off" au champ input.', 200, 'MEDIUM', '<!-- Ajoutez placeholder et autocomplete ci-dessous -->
<input type="text">', '(?s).*<input\s+.*placeholder="Entrez votre nom".*autocomplete="off".*/?>.*', 'world-1', '8. Formulaires', 'HTML5', 'Attributs de saisie', 'placeholder affiche un exemple visuel et autocomplete contrôle les suggestions.'),
('html-9-1', '9.1 La Grille de Compte (table)', 'Déclarez un tableau avec les balises <table></table>.', 100, 'EASY', '<!-- Déclarez la balise <table> ci-dessous -->
', '(?s).*<table\s*>\s*</table\s*>.*', 'world-1', '9. Tableaux', 'HTML5', 'Structure Table', '<table> crée une grille de données.'),
('html-9-2', '9.2 La Division des Registres (thead / tbody / tfoot)', 'Structurez le tableau avec <thead></thead>, <tbody></tbody> et <tfoot></tfoot>.', 200, 'MEDIUM', '<table>
  <!-- Ajoutez thead, tbody et tfoot ici -->
</table>', '(?s).*<table\s*>.*<thead\s*>\s*</thead\s*>.*<tbody\s*>\s*</tbody\s*>.*<tfoot\s*>\s*</tfoot\s*>.*</table\s*>.*', 'world-1', '9. Tableaux', 'HTML5', 'Sections du Tableau', '<thead>, <tbody> et <tfoot> séparent la structure du tableau.'),
('html-9-3', '9.3 L''Alignement des Rangées (tr / th / td)', 'Créez une rangée <tr> contenant l''en-tête <th>Rang</th> et la donnée <td>1</td>.', 150, 'EASY', '<!-- Rédigez le tr avec son th et td ci-dessous -->
', '(?s).*<tr\s*>.*<th\s*>Rang</th\s*>.*<td\s*>1</td\s*>.*</tr\s*>.*', 'world-1', '9. Tableaux', 'HTML5', 'Lignes & Cellules', '<tr> représente les lignes, <th> les en-têtes et <td> les cellules.'),
('html-9-4', '9.4 Le Titre du Registre (caption)', 'Ajoutez la légende <caption>Inventaire du Sac</caption> dans le tableau.', 150, 'EASY', '<table>
  <!-- Ajoutez la balise caption ci-dessous -->
</table>', '(?s).*<caption\s*>Inventaire du Sac</caption\s*>.*', 'world-1', '9. Tableaux', 'HTML5', 'Légende Caption', '<caption> donne un titre explicatif au tableau.'),
('html-9-5', '9.5 L''Étendue des En-têtes (scope)', 'Spécifiez scope="col" sur la cellule <th scope="col">Nom</th>.', 200, 'MEDIUM', '<tr>
  <!-- Ajoutez scope="col" au th ci-dessous -->
  <th>Nom</th>
</tr>', '(?s).*<th\s+scope="col"\s*>Nom</th\s*>.*', 'world-1', '9. Tableaux', 'HTML5', 'Portée Scope', 'scope="col" indique à quelle colonne se rapporte l''en-tête.'),
('html-9-6', '9.6 La Fusion des Cases (colspan / rowspan)', 'Fusionnez deux colonnes avec colspan="2" sur une cellule <td>.', 250, 'HARD', '<tr>
  <!-- Ajoutez colspan="2" à la cellule td ci-dessous -->
  <td>Fusion de deux cellules</td>
</tr>', '(?s).*<td\s+colspan="2"\s*>Fusion de deux cellules</td\s*>.*', 'world-1', '9. Tableaux', 'HTML5', 'Fusion Colspan & Rowspan', 'colspan s''étend sur plusieurs colonnes.'),
('html-10-1', '10.1 Le Coffre Accordéon (details / summary)', 'Créez un accordéon avec <details> et <summary>Voir le Secret</summary>.', 150, 'EASY', '<details>
  <!-- Ajoutez le summary Voir le Secret ci-dessous -->
  <p>Le secret est révélé !</p>
</details>', '(?s).*<details\s*>.*<summary\s*>Voir le Secret</summary\s*>.*<p\s*>Le secret est révélé !</p\s*>.*</details\s*>.*', 'world-1', '10. Éléments interactifs natifs', 'HTML5', 'Accordéon Details & Summary', '<details> et <summary> créent un accordéon repliable sans JavaScript.'),
('html-10-2', '10.2 Le Portail de Dialogue (dialog)', 'Affichez une modale native avec <dialog open><p>Contenu modal</p></dialog>.', 200, 'MEDIUM', '<!-- Créez la modale <dialog open> ci-dessous -->
', '(?s).*<dialog\s+open\s*>.*<p\s*>Contenu modal</p\s*>.*</dialog\s*>.*', 'world-1', '10. Éléments interactifs natifs', 'HTML5', 'Modal Dialog Native', '<dialog> crée des boîtes de dialogue et modales natives.'),
('html-10-3', '10.3 Le Sortilège Surgissant (popover)', 'Reliez un bouton popovertarget="mon-popover" à un élément <div id="mon-popover" popover>.', 250, 'HARD', '<button popovertarget="mon-popover">Afficher popover</button>
<!-- Créez le div popover id="mon-popover" ci-dessous -->
', '(?s).*<button\s+popovertarget="mon-popover"\s*>Afficher popover</button\s*>.*<div\s+.*id="mon-popover".*popover.*>Popover natif</div>.*', 'world-1', '10. Éléments interactifs natifs', 'HTML5', 'API Popover HTML5', 'L''API Popover permet l''affichage de fenêtres au-dessus de l''interface sans JS.'),
('html-11-4', '11.4 L''Écouteur Sacré (lecteur d''écran)', 'Exposez le span aux synthèses vocales avec aria-hidden="false".', 200, 'MEDIUM', '<!-- Ajoutez aria-hidden="false" au span ci-dessous -->
<span>Information vocale</span>', '(?s).*aria-hidden="false".*', 'world-1', '11. Accessibilité', 'HTML5, a11y', 'Support Lecteur d''Écran', 'aria-hidden contrôle la visibilité pour les lecteurs d''écran.'),
('html-11-5', '11.5 Le Descriptif d''Image (alt)', 'Ajoutez le texte alternatif alt="Carte du royaume" à l''image.', 100, 'EASY', '<!-- Ajoutez alt="Carte du royaume" à l''image ci-dessous -->
<img src="carte.png">', '(?s).*<img\s+.*alt="Carte du royaume".*/?>.*', 'world-1', '11. Accessibilité', 'HTML5, a11y', 'Attribut Alt', 'L''attribut alt transmet la description de l''image aux personnes malvoyantes.'),
('html-11-6', '11.6 Les Sorts d''Assistance (ARIA)', 'Attribuez le rôle ARIA d''alerte avec role="alert".', 200, 'MEDIUM', '<!-- Ajoutez role="alert" au div ci-dessous -->
<div>Attention piège !</div>', '(?s).*<div\s+role="alert"\s*>Attention piège !</div>.*', 'world-1', '11. Accessibilité', 'HTML5, ARIA', 'Attributs & Rôles ARIA', 'role="alert" force l''annonce immédiate du message par le lecteur d''écran.'),
('html-11-7', '11.7 La Déclaration d''Identité (nom accessible)', 'Fournissez le libellé vocal aria-label="Fermer la fenêtre" au bouton.', 200, 'MEDIUM', '<!-- Ajoutez aria-label="Fermer la fenêtre" au bouton ci-dessous -->
<button>X</button>', '(?s).*<button\s+aria-label="Fermer la fenêtre"\s*>X</button\s*>.*', 'world-1', '11. Accessibilité', 'HTML5, ARIA', 'Nom Accessible aria-label', 'aria-label donne une étiquette vocale quand le bouton n''affiche qu''une icône.'),
('html-12-1', '12.1 La Dไลย du Chargement (async / defer)', 'Ajoutez l''attribut defer pour charger le script sans bloquer le rendu.', 200, 'MEDIUM', '<!-- Ajoutez l''attribut defer à la balise script ci-dessous -->
<script src="magie.js"></script>', '(?s).*<script\s+.*src="magie\.js".*defer.*></script\s*>.*', 'world-1', '12. Performance', 'HTML5, JS', 'Chargement Async & Defer', 'defer charge le JS en arrière-plan et l''exécute après la fin de l''analyse HTML.'),
('html-12-2', '12.2 L''Anticipation des Ressources (preload)', 'Préchargez la police d''écriture avec <link rel="preload" href="font.woff2" as="font" type="font/woff2" crossorigin>.', 250, 'HARD', '<!-- Déclarez le rel="preload" ci-dessous -->
', '(?s).*<link\s+.*rel="preload".*href="font\.woff2".*as="font".*/?>.*', 'world-1', '12. Performance', 'HTML5', 'Resource Hint Preload', 'rel="preload" anticipe le téléchargement prioritaire des polices critiques.'),
('html-12-3', '12.3 Le Pont Préalable (preconnect)', 'Établissez une pré-connexion avec <link rel="preconnect" href="https://fonts.googleapis.com">.', 200, 'MEDIUM', '<!-- Déclarez le rel="preconnect" ci-dessous -->
', '(?s).*<link\s+.*rel="preconnect".*href="https://fonts\.googleapis\.com".*/?>.*', 'world-1', '12. Performance', 'HTML5', 'Resource Hint Preconnect', 'preconnect anticipe la poignée de main réseau avec un domaine externe.'),
('html-12-4', '12.4 L''Invocation Différée (lazy loading)', 'Ajoutez l''attribut loading="lazy" à la balise img.', 150, 'EASY', '<!-- Ajoutez loading="lazy" à l''image ci-dessous -->
<img src="fond.jpg" alt="Fond">', '(?s).*<img\s+.*loading="lazy".*/?>.*', 'world-1', '12. Performance', 'HTML5', 'Chargement Différé Lazy', 'loading="lazy" retarde le téléchargement des images jusqu''à ce qu''elles apparaissent au défilement.'),
('html-12-5', '12.5 La Compression des Fresques (optimisation des médias)', 'Servez l''image optimisée au format WebP : <img src="illustration.webp" alt="Illustration optimisée">.', 200, 'MEDIUM', '<!-- Insérez la balise img src="illustration.webp" ci-dessous -->
', '(?s).*<img\s+.*src="illustration\.webp".*/?>.*', 'world-1', '12. Performance', 'HTML5', 'Format Médias Optimisé', 'Le format WebP réduit significativement la taille des images sans perte visuelle.'),
('html-13-1', '13.1 Le Bouclier Anti-XSS (XSS)', 'Échappez le script avec les entités HTML &lt;script&gt;alert(1)&lt;/script&gt;.', 200, 'MEDIUM', '<p>
  <!-- Échappez la balise script ci-dessous avec &lt;script&gt; -->
</p>', '(?s).*&lt;script&gt;.*', 'world-1', '13. Sécurité', 'HTML5, Sécurité', 'Protection Injection XSS', 'L''échappement HTML transforme les caractères < et > en entités pour neutraliser les attaques XSS.'),
('html-13-2', '13.2 La Prison Magique (iframe sandbox)', 'Ajoutez l''attribut de sécurité sandbox à l''iframe.', 250, 'HARD', '<!-- Ajoutez l''attribut sandbox à l''iframe ci-dessous -->
<iframe src="https://externe.dev"></iframe>', '(?s).*<iframe\s+.*sandbox.*/?>.*', 'world-1', '13. Sécurité', 'HTML5, Sécurité', 'Attribut Sandbox iFrame', 'L''attribut sandbox isole une iframe et désactive l''exécution des scripts non autorisés.'),
('html-13-3', '13.3 Le Décret de Sécurité (CSP)', 'Spécifiez la meta CSP <meta http-equiv="Content-Security-Policy" content="default-src ''self''">.', 250, 'HARD', '<!-- Déclarez la meta Content-Security-Policy ci-dessous -->
', '(?s).*Content-Security-Policy.*', 'world-1', '13. Sécurité', 'HTML5, Sécurité', 'Content Security Policy', 'La politique CSP bloque l''exécution de scripts tiers non approuvés.'),
('html-13-4', '13.4 La Purification des Saisies (données utilisateur)', 'Ajoutez la contrainte d''expression régulière pattern="[A-Za-z0-9]+" à l''input.', 200, 'MEDIUM', '<!-- Ajoutez pattern="[A-Za-z0-9]+" à l''input ci-dessous -->
<input type="text" name="code">', '(?s).*pattern="\[A-Za-z0-9\]\+".*', 'world-1', '13. Sécurité', 'HTML5, Sécurité', 'Sanitisation des données', 'L''attribut pattern restreint les caractères autorisés dès la saisie dans le navigateur.'),
('html-14-1', '14.1 L''Épreuve du W3C (validation HTML)', 'Rédigez la structure complète conforme W3C (DOCTYPE, html lang="fr", head avec title, et body).', 150, 'EASY', '<!-- Rédigez le document HTML5 conforme W3C ci-dessous -->
', '(?s).*<!DOCTYPE\s+html.*', 'world-1', '14. Qualité', 'HTML5', 'Validateur W3C', 'La conformité W3C garantit l''absence d''erreurs de syntaxe et un rendu uniforme.'),
('html-14-2', '14.2 La Purge des Anciennes Runes (éléments deprecated)', 'Remplacez le style obsolète par la balise moderne <span style="color: red;">Texte rouge</span>.', 150, 'EASY', '<!-- Écrivez le span avec style="color: red;" ci-dessous -->
', '(?s).*<span\s+style="color:\s*red;?"\s*>Texte rouge</span>.*', 'world-1', '14. Qualité', 'HTML5', 'Nettoyage Éléments Obsolètes', 'HTML5 sépare la structure (HTML) du style visuel (CSS). Les balises obsolètes sont dépréciées.'),
('html-14-3', '14.3 La Vision DevTools (DevTools)', 'Ajoutez la classe class="inspect-target" à l''élément div.', 100, 'EASY', '<!-- Ajoutez class="inspect-target" au div ci-dessous -->
<div></div>', '(?s).*inspect-target.*', 'world-1', '14. Qualité', 'HTML5, DevTools', 'Inspection DOM DevTools', 'Inspecter les éléments avec DevTools permet d''analyser l''arbre DOM temps réel et les styles appliqués.'),
('html-14-4', '14.4 Le Miroir du DOM (HTML Source vs DOM)', 'Créez l''élément racine <div id="dom-root"></div>.', 100, 'EASY', '<!-- Créez le div id="dom-root" ci-dessous -->
', '(?s).*<div\s+id="dom-root"\s*>\s*</div>.*', 'world-1', '14. Qualité', 'HTML5, DOM', 'HTML Source vs Arbre DOM', 'Le fichier HTML est le texte statique. Le DOM est l''objet résidant en mémoire vive modifiable par JS.'),
('html-16-1', '16.1 Quiz 1 : L''Épreuve du Scribe (Structure & Head)', 'Testez vos connaissances en 3 questions sur la structure ancestrale HTML5, le DOCTYPE, le bloc <head> et l''arbre DOM. Indiquez votre réponse (''A'', ''B'', ''C'' ou ''D'') dans l''éditeur à droite.', 200, 'EASY', '<!-- QUIZ 1 : Remplacez data-reponse="" par "A", "B", "C" ou "D" -->

<!-- Q1: Quelle balise obligatoire déclare la version HTML5 au navigateur ? -->
<!-- A: <html5> | B: <!DOCTYPE html> | C: <meta charset="5"> | D: <header> -->
<question id="1" data-reponse=""></question>

<!-- Q2: Quelle section englobe le titre <title> et l''encodage charset ? -->
<!-- A: <body> | B: <section> | C: <head> | D: <aside> -->
<question id="2" data-reponse=""></question>

<!-- Q3: Quel est l''élément racine de l''arbre DOM ? -->
<!-- A: <html> | B: <body> | C: <!DOCTYPE> | D: <document> -->
<question id="3" data-reponse=""></question>', '(?s).*<question\s+id="1"\s+data-reponse="B"\s*></question\s*>.*<question\s+id="2"\s+data-reponse="C"\s*></question\s*>.*<question\s+id="3"\s+data-reponse="A"\s*></question\s*>.*', 'world-1', '16. 🎓 Quiz & Évaluation des Connaissances HTML5', 'Quiz, HTML5', 'Contrôle de Connaissances', 'Explication théorique du Quiz 1 :
- Q1 (B) : <!DOCTYPE html> informe le navigateur d''utiliser les normes HTML5 modernes.
- Q2 (C) : Le bloc <head> héberge les métadonnées non affichées directement sur la page.
- Q3 (A) : L''élément <html> constitue la racine principale de tout document HTML5.'),
('html-16-2', '16.2 Quiz 2 : L''Épreuve du Cartographe (Texte, Liens & Listes)', 'Testez vos connaissances en 3 questions sur la hiérarchie des titres, la sémantique textuelle, les liens hypertextes et les listes. Indiquez votre réponse (''A'', ''B'', ''C'' ou ''D'') dans l''éditeur.', 200, 'EASY', '<!-- QUIZ 2 : Remplacez data-reponse="" par "A", "B", "C" ou "D" -->

<!-- Q1: Quel niveau de titre représente le titre principal unique d''une page web ? -->
<!-- A: <h6> | B: <title> | C: <h1> | D: <header> -->
<question id="1" data-reponse=""></question>

<!-- Q2: Quel attribut obligatoire de la balise <a> définit l''URL de destination ? -->
<!-- A: src | B: href | C: link | D: target -->
<question id="2" data-reponse=""></question>

<!-- Q3: Quelle balise permet de créer une liste ordonnée numérotée ? -->
<!-- A: <ul> | B: <list> | C: <ol> | D: <dl> -->
<question id="3" data-reponse=""></question>', '(?s).*<question\s+id="1"\s+data-reponse="C"\s*></question\s*>.*<question\s+id="2"\s+data-reponse="B"\s*></question\s*>.*<question\s+id="3"\s+data-reponse="C"\s*></question\s*>.*', 'world-1', '16. 🎓 Quiz & Évaluation des Connaissances HTML5', 'Quiz, HTML5', 'Contrôle de Connaissances', 'Explication théorique du Quiz 2 :
- Q1 (C) : Chaque page doit posséder un unique <h1> pour sa structuration hiérarchique et le SEO.
- Q2 (B) : L''attribut ''href'' (Hypertext Reference) indique la cible de l''ancre <a>.
- Q3 (C) : <ol> génère une liste ordonnée (1, 2, 3...) contrairement à <ul> (puces non ordonnées).'),
('html-16-3', '16.3 Quiz 3 : L''Épreuve du Maître (Sémantique, Formulaires & A11y)', 'Testez vos connaissances en 3 questions sur les balises sémantiques modernes, la validation des formulaires et l''accessibilité web (a11y). Indiquez votre réponse (''A'', ''B'', ''C'' ou ''D'') dans l''éditeur.', 250, 'MEDIUM', '<!-- QUIZ 3 : Remplacez data-reponse="" par "A", "B", "C" ou "D" -->

<!-- Q1: Quelle balise sémantique définit la zone de navigation principale ? -->
<!-- A: <nav> | B: <menu> | C: <aside> | D: <header> -->
<question id="1" data-reponse=""></question>

<!-- Q2: Quel attribut lie un <label> à son champ <input> ? -->
<!-- A: name | B: for | C: id | D: value -->
<question id="2" data-reponse=""></question>

<!-- Q3: Quel attribut d''image offre une description textuelle alternative pour l''accessibilité ? -->
<!-- A: title | B: src | C: alt | D: aria-name -->
<question id="3" data-reponse=""></question>', '(?s).*<question\s+id="1"\s+data-reponse="A"\s*></question\s*>.*<question\s+id="2"\s+data-reponse="B"\s*></question\s*>.*<question\s+id="3"\s+data-reponse="C"\s*></question\s*>.*', 'world-1', '16. 🎓 Quiz & Évaluation des Connaissances HTML5', 'Quiz, HTML5', 'Contrôle de Connaissances', 'Explication théorique du Quiz 3 :
- Q1 (A) : <nav> entoure les blocs de liens de navigation majeurs.
- Q2 (B) : L''attribut ''for'' du label doit correspondre à l''attribut ''id'' du champ de saisie associé.
- Q3 (C) : L''attribut ''alt'' fournit la description accessible de l''image pour l''accessibilité.'),
('a11y-1-1', '1.1 Le Bouclier de l''A11y (Nom Accessible & aria-label)', 'Ajoutez un nom accessible explicite à un bouton d''action iconique à l''aide de l''attribut aria-label="Fermer le grimoire".', 150, 'EASY', '<!-- Ajoutez l''attribut aria-label="Fermer le grimoire" à ce bouton d''icône -->
<button type="button">
  <span>✖</span>
</button>', '(?s).*<button\s+.*aria-label="Fermer le grimoire".*>', 'world-2', '1. Les Fondements de l''Accessibilité (A11y & handicap)', 'HTML5, A11y', 'Nom Accessible & Design Inclusif', 'Théorie - Les Fondements de l''A11y :
- L''accessibilité numérique (A11y) garantit que les services web sont utilisables par tous (handicaps visuels, moteurs, temporaires ou situationnels).
- Lorsqu''un bouton n''a pas de texte visible (uniquement une icône), il est obligatoire de lui fournir un nom accessible via `aria-label` pour qu''il soit dicté aux lecteurs d''écran.'),
('a11y-2-1', '2.1 L''Étalon des Standards (Déclaration WCAG 2.2 AA)', 'Déclarez le niveau d''accessibilité visé dans le pied de page du site avec l''élément sémantique <footer>Conforme WCAG 2.2 Niveau AA</footer>.', 150, 'EASY', '<!-- Créez le footer d''accessibilité avec la déclaration WCAG ci-dessous -->
', '(?s).*<footer\s*>.*Conforme WCAG 2\.2 Niveau AA.*</footer\s*>.*', 'world-2', '2. Le Codex des Standards (WCAG & normes)', 'HTML5, WCAG', 'Engagements & Conformité WCAG', 'Théorie - Les Standards WCAG :
- Les WCAG (Web Content Accessibility Guidelines) définissent trois niveaux de conformité : A (minimal), AA (standard légal et recommandé) et AAA (avancé).
- Le niveau WCAG 2.2 AA constitue la cible internationale obligatoire pour la plupart des applications et sites web professionnels.'),
('a11y-3-1', '3.1 La Purge des Faux Boutons (Semantic HTML First)', 'La première règle de l''accessibilité est d''utiliser le HTML sémantique natif ! Remplacez le div générique cliquable par un vrai bouton sémantique <button type="button">.', 150, 'EASY', '<!-- ÉLIMINEZ LA MAUVAISE PRATIQUE : Remplacez ce <div> par un véritable bouton <button type="button"> Lancer le sort </button> -->
<div onclick="lancerSort()">Lancer le sort</div>', '(?s).*<button\s+type="button"\s*>Lancer le sort</button\s*>.*', 'world-2', '3. Le Pouvoir du HTML Natif (Semantic HTML First)', 'HTML5, A11y', 'Sémantique Native vs ARIA', 'Théorie - HTML Natif d''abord :
- Un <div onclick="..."> n''est ni focalisable au clavier (Touche Tab), ni activable par les touches Espace ou Entrée, et n''annonce aucun rôle aux lecteurs d''écran.
- En utilisant <button type="button">, le navigateur offre gratuitement la gestion du focus clavier, l''événement ''click'' au clavier, et le rôle approprié dans l''Arbre d''Accessibilité.'),
('a11y-4-1', '4.1 Le Focus Clavier Apprivoisé (tabindex & Focus Visible)', 'Rendez la carte d''équipement personnalisée focalisable au clavier en utilisant l''attribut tabindex="0", sans altérer l''ordre naturel des éléments.', 150, 'EASY', '<!-- Ajoutez l''attribut tabindex approprié pour rendre ce composant interactif focalisable dans la séquence Tab naturelle -->
<div class="artefact-card">
  <h3>Épée Légendaire</h3>
</div>', '(?s).*<div\s+.*tabindex="0".*>\s*<h3>Épée Légendaire</h3>\s*</div>.*', 'world-2', '4. Le Royaume du Clavier (Navigation clavier)', 'HTML5, CSS3, A11y', 'Navigation Clavier & Focus', 'Théorie - Le Focus Clavier :
- `tabindex="0"` insère l''élément dans la séquence séquentielle de tabulation du clavier selon sa position dans le DOM.
- `tabindex="-1"` rend l''élément focalisable uniquement par JavaScript via `.focus()`, sans l''insérer dans la tabulation.
- RÈGLE D''OR : N''utilisez JAMAIS de `tabindex` positif (>0), car cela brise l''ordre logique de navigation !'),
('a11y-5-1', '5.1 La Mascarade des Lecteurs d''écran (.sr-only)', 'Créer une classe CSS .sr-only qui masque visuellement le texte.', 200, 'MEDIUM', '<style>\n.sr-only {\n  position: absolute;\n  width: 1px;\n  height: 1px;\n  overflow: hidden;\n  clip: rect(0, 0, 0, 0);\n}\n</style>\n<button type="button">\n  <span>icon</span>\n  <span class="sr-only">Fermer la fenêtre</span>\n</button>', '(?s).*\.sr-only\s*\{.*position:\s*absolute.*width:\s*1px.*height:\s*1px.*overflow:\s*hidden.*clip:\s*rect.*\}.*<span\s+class="sr-only"\s*>Fermer la fenêtre</span>.*', 'world-2', '5. La Vision des Lecteurs d''écran (Screen Readers)', 'HTML5, CSS3, A11y', 'Screen Readers & Arbre d''Accessibilité', 'Théorie - Masquage pour Lecteur d''écran...'),
('a11y-7-1', '7.1 Le Duel des Couleurs (Contraste 4.5:1 & Double Signal)', 'Assurez la lisibilité du texte de notification en ajustant sa couleur CSS pour respecter le ratio 4.5:1 et ajoutez une icône/symbole à côté du texte rouge d''erreur.', 200, 'MEDIUM', '<style>
/* Corrigez la couleur claire #9ca3af sur fond blanc par une couleur sombre accessible #1f2937 */
.alert-error {
  background-color: #ffffff;
  color: #9ca3af;
}
</style>

<!-- Ajoutez un symbole ou texte explicite (ex: "⚠️ Erreur : ") pour ne pas dépendre uniquement de la couleur -->
<div class="alert-error">
  <span>Le sortilège a échoué.</span>
</div>', '(?s).*color:\s*#1f2937.*⚠️\s*Erreur\s*:?\s*Le sortilège a échoué\..*', 'world-2', '7. Le Labyrinthe des Couleurs (Contraste & Perception)', 'HTML5, CSS3, A11y', 'Contrôle de Connaissances', 'Théorie - Couleurs et Contrastes :
- WCAG 2.2 AA exige un ratio de contraste d''au moins 4.5:1 pour le texte normal (et 3:1 pour le texte large >= 18pt).
- Ne transmettez JAMAIS une information uniquement par la couleur (ex: rouge pour erreur, vert pour succès) car les utilisateurs daltoniens ou sur écran à faible luminosité ne pourront pas la percevoir. Associez toujours un symbole, texte ou icône.'),
('a11y-8-1', '8.1 Le Serment des Unités Relatives (rem & Zoom 200%)', 'Convertissez les tailles de police de pixels fixes vers des unités relatives rem (basées sur 16px de base) afin que la mise en page s''adapte au zoom navigateur à 200%.', 150, 'EASY', '<style>
/* Convertissez 32px (Titre) et 16px (Corps) en unités rem (Rappel: 1rem = 16px) */
h1 {
  font-size: 32px;
}

p {
  font-size: 16px;
}
</style>', '(?s).*h1\s*\{\s*font-size:\s*2rem;?\s*\}.*p\s*\{\s*font-size:\s*1rem;?\s*\}.*', 'world-2', '8. Le Temple de la Typographie (Lisibilité & Affichage)', 'CSS3, A11y', 'Typographie & Zoom Accessible', 'Théorie - Unités Relatives & Zoom :
- L''utilisation de `rem` (Root EM) permet au texte de se redimensionner automatiquement lorsque l''utilisateur modifie la taille de police par défaut de son navigateur.
- WCAG exige que les pages web restent entièrement lisibles et fonctionnelles lorsqu''elles sont zoomées à 200% sans nécessiter de défilement horizontal bidirectionnel (Reflow).'),
('a11y-9-1', '9.1 L''Alliance du Formulaire Accessible (label & aria-describedby)', 'Liez explicitement l''étiquette <label> au champ <input id="pseudo"> et reliez la consigne d''aide au champ grâce à l''attribut aria-describedby.', 200, 'MEDIUM', '<!-- Formulaire d''inscription accessible -->
<label for="">Nom de l''Aventurier</label>
<input type="text" id="pseudo" aria-describedby="" required />
<p id="pseudo-help">Le pseudo doit comporter au moins 3 caractères.</p>', '(?s).*<label\s+for="pseudo"\s*>Nom de l''Aventurier</label\s*>.*<input\s+.*id="pseudo".*aria-describedby="pseudo-help".*/?>.*', 'world-2', '9. La Guilde des Formulaires Accessibles', 'HTML5, A11y', 'Formulaires Accessibles & Libellés', 'Théorie - Formulaires Accessibles :
- L''attribut `for="id"` du `<label>` agrandit la zone cliquable du champ et permet aux lecteurs d''écran de lire l''étiquette au moment où le champ prend le focus.
- `aria-describedby="id-aide"` fournit des instructions ou consignes d''erreur complémentaires qui sont automatiquement dictées par les technologies d''assistance dès l''entrée dans le champ.'),
('a11y-10-1', '10.1 Le Dialogue de la Fenêtre Modale (role=dialog)', 'Déclarez une fenêtre modale accessible en définissant les attributs ARIA natifs role="dialog", aria-modal="true" et aria-labelledby sur le conteneur principal.', 200, 'MEDIUM', '<!-- Configurez le conteneur de la boîte de dialogue modale -->
<div class="modal-window">
  <h2 id="modal-title">Confirmation du Grimoire</h2>
  <p>Voulez-vous enregistrer vos sorts ?</p>
</div>', '(?s).*<div\s+.*role="dialog".*aria-modal="true".*aria-labelledby="modal-title".*>', 'world-2', '10. Le Royaume des Composants Interactifs', 'HTML5, ARIA, A11y', 'Patterns de Composants Accessibles', 'Théorie - Boîtes de Dialogue Modales :
- `role="dialog"` informe le lecteur d''écran qu''il s''agit d''une fenêtre de dialogue autonome.
- `aria-modal="true"` signale que le reste du document sous-jacent est inactif/inaccessible tant que la modale est ouverte.
- `aria-labelledby="modal-title"` lie le titre principal de la modale comme nom accessible pour l''annonce initiale.'),
('a11y-11-1', '11.1 Le Grimoire des États ARIA (aria-expanded & aria-controls)', 'Communiquez l''état d''ouverture d''un accordéon déroulant aux technologies d''assistance en ajoutant aria-expanded="true" et aria-controls="acc-panel" sur le bouton.', 200, 'MEDIUM', '<!-- Bouton de déclenchement de l''accordéon -->
<button type="button">
  Afficher la quête
</button>

<div id="acc-panel" class="accordion-content">
  <p>Détails secrets de la quête...</p>
</div>', '(?s).*<button\s+.*aria-expanded="true".*aria-controls="acc-panel".*>.*Afficher la quête.*</button\s*>.*', 'world-2', '11. Le Grimoire ARIA (WAI-ARIA)', 'HTML5, ARIA, A11y', 'WAI-ARIA Roles & States', 'Théorie - WAI-ARIA (Rôles, États & Propriétés) :
- `aria-expanded="true|false"` indique dynamiquement si la section associée est dépliée ou repliée.
- `aria-controls="id-panneau"` établit une relation programmatique directe entre le bouton déclencheur et le panneau contrôlé.
- RÈGLE D''OR ARIA : ''No ARIA is better than bad ARIA''. Utilisez d''abord le HTML sémantique avant de rajouter des attributs ARIA.'),
('a11y-12-1', '12.1 Le Sanctuaire du Mouvement (prefers-reduced-motion)', 'Utilisez la media query CSS prefers-reduced-motion pour désactiver l''animation de rotation lorsque l''utilisateur a activé l''option de réduction des mouvements dans son système.', 150, 'EASY', '<style>
.spinner {
  animation: spin 2s infinite linear;
}

/* Ajoutez la media query (prefers-reduced-motion: reduce) pour désactiver l''animation de .spinner (animation: none;) */

</style>', '(?s).*@media\s*\(\s*prefers-reduced-motion:\s*reduce\s*\)\s*\{\s*\.spinner\s*\{\s*animation:\s*none;?\s*\}\s*\}.*', 'world-2', '12. Le Royaume du Mouvement (Animations & Motion)', 'CSS3, A11y', 'Animations & Accessibility Motion', 'Théorie - Mouvement & Accessibilité :
- Les animations intenses, scintillements ou effets de parallaxe peuvent causer des vertiges, nausées et désorientations chez les personnes souffrant de troubles vestibulaires.
- `@media (prefers-reduced-motion: reduce)` permet de respecter le choix du système d''exploitation de l''utilisateur en désactivant ou simplifiant les animations non essentielles.'),
('a11y-13-1', '13.1 L''Annonceur Dynamique (aria-live = polite & role=status)', 'Configurez la zone de notification dynamique pour vocaliser poliment les messages système mis à jour en JavaScript sans interrompre la lecture en cours.', 200, 'MEDIUM', '<!-- Créez un conteneur de statut dynamique avec aria-live="polite" et role="status" -->
<div class="toast-container">
  <span>Sauvegarde automatique réussie !</span>
</div>', '(?s).*<div\s+.*aria-live="polite".*role="status".*>.*Sauvegarde automatique réussie !.*</div>.*', 'world-2', '13. Le Donjon des Interfaces Dynamiques (JavaScript & A11y)', 'HTML5, ARIA, JavaScript, A11y', 'Dynamic DOM & Live Regions', 'Théorie - Live Regions (Régions en direct) :
- Lors des injections de contenu en JavaScript (AJAX, SPA), l''écran change sans rechargement de page. Les lecteurs d''écran ne le remarquent pas par défaut.
- `aria-live="polite"` vocalise l''information au premier moment de silence de l''utilisateur.
- `aria-live="assertive"` (ou `role="alert"`) interrompt immédiatement la lecture pour les messages critiques/erreurs graves.'),
('a11y-14-1', '14.1 Le Squelette de Chargement (aria-busy & Progressive Enhancement)', 'Signalez aux technologies d''assistance qu''un bloc de données est en cours de chargement asynchrone grâce à l''attribut aria-busy="true".', 150, 'EASY', '<!-- Indiquez que la section est en cours de chargement avec aria-busy="true" -->
<section class="card-skeleton">
  <p>Chargement des statistiques...</p>
</section>', '(?s).*<section\s+.*aria-busy="true".*>.*Chargement des statistiques\.\.\.*</section\s*>.*', 'world-2', '14. Le Bastion des Performances & de l''Accessibilité', 'HTML5, ARIA, A11y', 'Performance & Progressivité', 'Théorie - Performance & Accessibilité :
- La performance réseau est une facette directe de l''accessibilité pour les utilisateurs sur connexions lentes ou appareils mobiles modestes.
- `aria-busy="true"` informe le lecteur d''écran d''attendre la fin de la mise à jour asynchrone avant de lire le contenu du bloc.'),
('a11y-15-1', '15.1 La Zone de Recherche Accessible (role=search)', 'Déclarez le bloc de recherche principale à l''aide de la balise sémantique <form role="search"> avec un champ de saisie <input type="search">.', 150, 'EASY', '<!-- Créez le formulaire de recherche avec role="search" ci-dessous -->
', '(?s).*<form\s+.*role="search".*>.*<input\s+.*type="search".*/?>.*</form\s*>.*', 'world-2', '15. Le Laboratoire des Tests (Accessibility Testing)', 'HTML5, ARIA, A11y', 'Landmarks & Structure de Recherche', 'Théorie - Landmarks de Recherche :
- Définir `role="search"` sur un formulaire permet aux utilisateurs de lecteurs d''écran d''accéder instantanément à la fonction de recherche via leurs raccourcis de navigation par repères (Landmarks).'),
('a11y-17-1', '17.1 La Langue Ancestrale (<html lang=fr>)', 'Déclarez la langue principale du document avec la balise racine <html lang="fr"> afin que les moteurs de synthèse vocale adaptent leur prononciation.', 150, 'EASY', '<!-- Déclarez la balise <html> avec l''attribut lang="fr" -->
<html></html>', '(?s).*<html\s+lang="fr"\s*></html\s*>.*', 'world-2', '17. La Forge de l''Accessibilité Continue (Development Workflow)', 'Quiz, Continuous A11y', 'Workflow, Design System & CI/CD', 'Théorie - Accessibilité Continue (Shift Left) :
- Intégrer l''A11y dès la phase de maquettage UI/UX (couleurs accessibles, composants de design system) évite les refontes lourdes en fin de cycle.
- L''inclusion d''une grille de contrôle (Checklist A11y) lors de la révision de code (Pull Request) et dans la Definition of Done assure un niveau de qualité pérenne.'),
('css-1-1', '1.1 Syntaxe & Déclaration CSS', 'Appliquez color: #3b82f6; et text-align: center; à .titre-royal.', 150, 'EASY', '<style>
</style>
<h1 class="titre-royal">Royaume</h1>', '(?s).* animate .*|\.titre-royal\s*\{.*color:\s*#3b82f6;?.*text-align:\s*center;?.*\}', 'world-3', '1. La Peau du Web (Introduction & Syntaxe CSS)', 'CSS3', 'Syntaxe CSS', 'CSS contrôle la présentation visuelle.'),
('css-1-2', '1.2 Suppression des Styles Inline', 'Remplacez le style inline par une règle .alerte { color: #ef4444; }.', 150, 'EASY', '<style>
</style>
<p class="alerte">Alerte</p>', '(?s).*\.alerte\s*\{.*color:\s*#ef4444;?.*\}.*<p\s+class=\"alerte\"\s*>Alerte</p>.*', 'world-3', '1. La Peau du Web (Introduction & Syntaxe CSS)', 'CSS3', 'Separation of Concerns', 'Évitez le style inline pour garder un code maintenable.'),
('css-1-3', '1.3 Commentaires CSS', 'Ajoutez un commentaire CSS /* Style des cartes */ au-dessus de la règle .carte { padding: 10px; }.', 150, 'EASY', '<style>
</style>
<div class="carte">Carte</div>', '(?s).*/\*\s*Style des cartes\s*\*/.*\.carte\s*\{.*padding:\s*10px;?.*\}.*', 'world-3', '1. La Peau du Web (Introduction & Syntaxe CSS)', 'CSS3', 'CSS Comments', 'Les commentaires /* ... */ documentent votre code CSS.'),
('css-1-4', '1.4 Organisation d''une Feuille CSS', 'Regroupez la réinitialisation de marge et padding sur * { margin: 0; padding: 0; }.', 150, 'EASY', '<style>
</style>', '(?s).*\*\s*\{.*margin:\s*0;?.*padding:\s*0;?.*\}.*', 'world-3', '1. La Peau du Web (Introduction & Syntaxe CSS)', 'CSS3', 'CSS Reset Base', 'Réinitialiser les marges par défaut évite les écarts inter-navigateurs.'),
('css-2-1', '2.1 Sélecteurs d''ID & de Classe', 'Stylez #hero { background: #1e1b4b; } et .badge { color: #f59e0b; }.', 150, 'EASY', '<style>
</style>
<div id="hero"><span class="badge">V</span></div>', '(?s).*#hero\s*\{.*background:\s*#1e1b4b;?.*\}.*\.badge\s*\{.*color:\s*#f59e0b;?.*\}.*', 'world-3', '2. Le Grimoire des Sélecteurs', 'CSS3', 'ID & Class Selectors', 'ID (#) pour élément unique, Classe (.) pour réutilisables.'),
('css-2-2', '2.2 Sélecteur d''Enfant Direct (>)', 'Stylez uniquement les li enfants directs de .menu (> li { color: #10b981; }).', 150, 'EASY', '<style>
</style>
<ul class="menu"><li>Un</li></ul>', '(?s).*\.menu\s*>\s*li\s*\{.*color:\s*#10b981;?.*\}.*', 'world-3', '2. Le Grimoire des Sélecteurs', 'CSS3', 'Direct Child Combinator', 'L''opérateur > cible les enfants immédiats.'),
('css-2-3', '2.3 Sélecteur d''Attribut ([data-rarete])', 'Ciblez [data-rarete="epic"] avec border: 2px solid #8b5cf6;.', 200, 'MEDIUM', '<style>
</style>
<div data-rarete="epic">Item</div>', '(?s).*\[data-rarete=\"epic\"\]\s*\{.*border:\s*2px\s+solid\s+#8b5cf6;?.*\}.*', 'world-3', '2. Le Grimoire des Sélecteurs', 'CSS3', 'Attribute Selectors', '[attr=val] permet un ciblage par attribut HTML.'),
('css-2-4', '2.4 Sélecteurs Adjacents (+)', 'Stylez le paragraphe suivant immédiatement un h2 avec h2 + p { color: #6366f1; }.', 150, 'EASY', '<style>
</style>
<h2>Titre</h2><p>Texte</p>', '(?s).*h2\s*\+\s*p\s*\{.*color:\s*#6366f1;?.*\}.*', 'world-3', '2. Le Grimoire des Sélecteurs', 'CSS3', 'Adjacent Sibling', 'L''opérateur + cible le frère immédiatement suivant.'),
('css-2-5', '2.5 Sélecteur Général Frère (~)', 'Ciblez tous les span suivants un input avec input ~ span { opacity: 0.8; }.', 150, 'EASY', '<style>
</style>
<input><span>1</span><span>2</span>', '(?s).*input\s*~\s*span\s*\{.*opacity:\s*0\.8;?.*\}.*', 'world-3', '2. Le Grimoire des Sélecteurs', 'CSS3', 'General Sibling', 'L''opérateur ~ cible tous les frères suivants.'),
('css-3-1', '3.1 Calcul de Spécificité', 'Surpassez la règle p générique avec #zone .texte { color: #f59e0b; }.', 200, 'MEDIUM', '<style>
p { color: grey; }
</style>
<div id="zone"><p class="texte">OK</p></div>', '(?s).*#zone\s+\.texte\s*\{.*color:\s*#f59e0b;?.*\}.*', 'world-3', '3. La Cascade & les Lois du Style', 'CSS3', 'CSS Specificity', 'Un ID + une classe l''emportent sur un simple sélecteur d''élément.'),
('css-3-2', '3.2 Surcharge avec !important (Bon usage)', 'Utilisez color: #ef4444 !important; sur la classe .force-danger pour garantir la priorité.', 150, 'EASY', '<style>
.force-danger { }
</style>', '(?s).*\.force-danger\s*\{.*color:\s*#ef4444\s*!important;?.*\}.*', 'world-3', '3. La Cascade & les Lois du Style', 'CSS3', '!important Flag', '!important annule la cascade normale.'),
('css-3-3', '3.3 Mot-clé unset & revert', 'Appliquez color: unset; sur .reset-color pour laisser l''héritage s''appliquer.', 150, 'EASY', '<style>
.reset-color { }
</style>', '(?s).*\.reset-color\s*\{.*color:\s*unset;?.*\}.*', 'world-3', '3. La Cascade & les Lois du Style', 'CSS3', 'CSS Unset Keyword', 'unset remet la valeur à sa valeur héritée ou initiale.'),
('css-3-4', '3.4 Héritage des Propriétés', 'Forcez le bouton à hériter la police du parent avec font-family: inherit;.', 150, 'EASY', '<style>
button { }
</style>', '(?s).*button\s*\{.*font-family:\s*inherit;?.*\}.*', 'world-3', '3. La Cascade & les Lois du Style', 'CSS3', 'Inheritance', 'Certains éléments comme button n''héritent pas par défaut des polices.'),
('css-4-1', '4.1 Padding, Border & Margin', 'Stylez .box avec width: 200px;, padding: 15px;, border: 1px solid #ccc; et margin: 10px;.', 150, 'EASY', '<style>
.box { }
</style>', '(?s).*\.box\s*\{.*width:\s*200px;?.*padding:\s*15px;?.*border:\s*1px\s+solid\s+#ccc;?.*margin:\s*10px;?.*\}.*', 'world-3', '4. Le Modèle de la Boîte', 'CSS3', 'Box Model Parts', 'Le box-model comprend content, padding, border et margin.'),
('css-4-2', '4.2 Sizing avec border-box', 'Appliquez box-sizing: border-box; sur .box-border.', 150, 'EASY', '<style>
.box-border { }
</style>', '(?s).*\.box-border\s*\{.*box-sizing:\s*border-box;?.*\}.*', 'world-3', '4. Le Modèle de la Boîte', 'CSS3', 'Border-box', 'border-box englobe padding et border dans width.'),
('css-4-3', '4.3 Contraintes Min & Max Width', 'Empêchez l''élément d''être trop étroit ou trop large avec min-width: 300px; et max-width: 800px;.', 150, 'EASY', '<style>
.container { }
</style>', '(?s).*\.container\s*\{.*min-width:\s*300px;?.*max-width:\s*800px;?.*\}.*', 'world-3', '4. Le Modèle de la Boîte', 'CSS3', 'Min/Max Dimensions', 'min-width et max-width encadrent la largeur réactive.'),
('css-4-4', '4.4 Overflow & Débordements', 'Autorisez le défilement vertical avec overflow-y: auto; et masquez l''horizontal avec overflow-x: hidden;.', 150, 'EASY', '<style>
.panel { }
</style>', '(?s).*\.panel\s*\{.*overflow-y:\s*auto;?.*overflow-x:\s*hidden;?.*\}.*', 'world-3', '4. Le Modèle de la Boîte', 'CSS3', 'Overflow Axes', 'overflow-x et overflow-y gèrent les axes séparément.'),
('css-5-1', '5.1 Unités rem scalable', 'Définissez font-size: 1.25rem; et padding: 1rem; sur .card.', 150, 'EASY', '<style>
.card { }
</style>', '(?s).*\.card\s*\{.*font-size:\s*1\.25rem;?.*padding:\s*1rem;?.*\}.*', 'world-3', '5. Les Dimensions & Unités', 'CSS3', 'Rem Units', 'rem est relatif à la racine html (16px).'),
('css-5-2', '5.2 Unités Viewport (vw & vh)', 'Définissez la hauteur de la section d''accueil avec height: 100vh;.', 150, 'EASY', '<style>
.hero { }
</style>', '(?s).*\.hero\s*\{.*height:\s*100vh;?.*\}.*', 'world-3', '5. Les Dimensions & Unités', 'CSS3', 'Viewport Height', '100vh correspond à 100% de la hauteur de la fenêtre.'),
('css-5-3', '5.3 Opérations avec calc()', 'Calculez width: calc(100% - 40px); sur .full-bleed.', 150, 'EASY', '<style>
.full-bleed { }
</style>', '(?s).*\.full-bleed\s*\{.*width:\s*calc\(\s*100%\s*-\s*40px\s*\);?.*\}.*', 'world-3', '5. Les Dimensions & Unités', 'CSS3', 'calc() Function', 'calc() combine unités relatives et absolues.'),
('css-5-4', '5.4 Typographie Fluide avec clamp()', 'Stylez font-size: clamp(1.2rem, 3vw, 2.5rem);.', 200, 'MEDIUM', '<style>
h1 { }
</style>', '(?s).*h1\s*\{.*font-size:\s*clamp\(\s*1\.2rem\s*,\s*3vw\s*,\s*2\.5rem\s*\);?.*\}.*', 'world-3', '5. Les Dimensions & Unités', 'CSS3', 'clamp() Fluid Font', 'clamp() garantit des limites basses et hautes.'),
('css-6-1', '6.1 Couleurs Hexadécimales & RGB', 'Appliquez background: #0f172a; et color: rgb(248, 250, 252); sur body.', 150, 'EASY', '<style>
body { }
</style>', '(?s).*body\s*\{.*background:\s*#0f172a;?.*color:\s*rgb\(\s*248\s*,\s*250\s*,\s*252\s*\);?.*\}.*', 'world-3', '6. Le Codex des Couleurs', 'CSS3', 'Hex & RGB Colors', 'Hex et RGB sont les formats couleur web historiques.'),
('css-6-2', '6.2 Couleurs HSL & Opacité RGBA', 'Définissez un fond semi-transparent avec background-color: rgba(59, 130, 246, 0.5);.', 150, 'EASY', '<style>
.overlay { }
</style>', '(?s).*\.overlay\s*\{.*background-color:\s*rgba\(\s*59\s*,\s*130\s*,\s*246\s*,\s*0\.5\s*\);?.*\}.*', 'world-3', '6. Le Codex des Couleurs', 'CSS3', 'RGBA Transparency', 'Le canal alpha (0.5) gère la transparence.'),
('css-6-3', '6.3 Dégradé Linéaire (linear-gradient)', 'Appliquez background: linear-gradient(135deg, #6366f1, #a855f7);.', 150, 'EASY', '<style>
.btn-gradient { }
</style>', '(?s).*\.btn-gradient\s*\{.*background:\s*linear-gradient\(\s*135deg\s*,\s*#6366f1\s*,\s*#a855f7\s*\);?.*\}.*', 'world-3', '6. Le Codex des Couleurs', 'CSS3', 'Linear Gradient', 'linear-gradient crée des fondus angulaires.'),
('css-6-4', '6.4 Couleurs Perceptuelles avec oklch()', 'Stylez color: oklch(0.7 0.15 140); sur .accent-oklch.', 200, 'MEDIUM', '<style>
.accent-oklch { }
</style>', '(?s).*\.accent-oklch\s*\{.*color:\s*oklch\(\s*0\.7\s+0\.15\s+140\s*\);?.*\}.*', 'world-3', '6. Le Codex des Couleurs', 'CSS3', 'oklch Color Space', 'oklch offre une uniformité de brillance perceptuelle.'),
('css-7-1', '7.1 Poids & Style de Police', 'Appliquez font-weight: 700; et font-style: italic; à .bold-italic.', 150, 'EASY', '<style>
.bold-italic { }
</style>', '(?s).*\.bold-italic\s*\{.*font-weight:\s*700;?.*font-style:\s*italic;?.*\}.*', 'world-3', '7. La Forge Typographique', 'CSS3', 'Font Weight & Style', 'font-weight définit l''épaisseur et font-style l''inclinaison.'),
('css-7-2', '7.2 Hauteur de Ligne & Interlettrage', 'Définissez line-height: 1.6; et letter-spacing: 1px; sur p.', 150, 'EASY', '<style>
p { }
</style>', '(?s).*p\s*\{.*line-height:\s*1\.6;?.*letter-spacing:\s*1px;?.*\}.*', 'world-3', '7. La Forge Typographique', 'CSS3', 'Text Spacing', 'Interlignage et espacement améliorent la lisibilité.'),
('css-7-3', '7.3 Transformations de Texte & Alignement', 'Réglez text-transform: uppercase; et text-align: center; sur h2.', 150, 'EASY', '<style>
h2 { }
</style>', '(?s).*h2\s*\{.*text-transform:\s*uppercase;?.*text-align:\s*center;?.*\}.*', 'world-3', '7. La Forge Typographique', 'CSS3', 'Text Alignment & Transform', 'text-transform passe en majuscules et text-align centre.'),
('css-7-4', '7.4 Tronquage Ellipsis', 'Ajoutez white-space: nowrap;, overflow: hidden; et text-overflow: ellipsis;.', 200, 'MEDIUM', '<style>
.truncate { }
</style>', '(?s).*\.truncate\s*\{.*white-space:\s*nowrap;?.*overflow:\s*hidden;?.*text-overflow:\s*ellipsis;?.*\}.*', 'world-3', '7. La Forge Typographique', 'CSS3', 'Ellipsis Truncate', 'Combinaison classique pour tronquer un texte long avec ''...''.'),
('css-8-1', '8.1 Propriété Display Block vs Inline', 'Passez .inline-badge en display: inline-block; avec width: 80px;.', 150, 'EASY', '<style>
.inline-badge { }
</style>', '(?s).*\.inline-badge\s*\{.*display:\s*inline-block;?.*width:\s*80px;?.*\}.*', 'world-3', '8. Le Royaume du Flux', 'CSS3', 'Inline-Block Display', 'inline-block permet d''appliquer une largeur à un élément en ligne.'),
('css-8-2', '8.2 Masquage d''Éléments (display: none)', 'Masquez complètement l''élément avec display: none;.', 150, 'EASY', '<style>
.hidden { }
</style>', '(?s).*\.hidden\s*\{.*display:\s*none;?.*\}.*', 'world-3', '8. Le Royaume du Flux', 'CSS3', 'Display None', 'display: none retire l''élément du flux d''affichage.'),
('css-8-3', '8.3 Contexte de Formatage flow-root', 'Résolvez le débordement de flottants avec display: flow-root;.', 150, 'EASY', '<style>
.clearfix { }
</style>', '(?s).*\.clearfix\s*\{.*display:\s*flow-root;?.*\}.*', 'world-3', '8. Le Royaume du Flux', 'CSS3', 'Flow-Root BFC', 'flow-root contient proprement tous ses enfants flottants.'),
('css-9-1', '9.1 Activation Flexbox & Direction', 'Configurez display: flex; et flex-direction: column; sur .menu-vert.', 150, 'EASY', '<style>
.menu-vert { }
</style>', '(?s).*\.menu-vert\s*\{.*display:\s*flex;?.*flex-direction:\s*column;?.*\}.*', 'world-3', '9. La Guilde Flexbox', 'CSS3', 'Flex Direction', 'flex-direction: column empile les éléments verticalement.'),
('css-9-2', '9.2 Alignements sur Axe Principal & Transversal', 'Centrez sur les 2 axes avec justify-content: center; et align-items: center;.', 150, 'EASY', '<style>
.flex-center { display: flex;
}
</style>', '(?s).*\.flex-center\s*\{.*justify-content:\s*center;?.*align-items:\s*center;?.*\}.*', 'world-3', '9. La Guilde Flexbox', 'CSS3', 'Flex Centering', 'Le centrage parfait en 2 propriétés Flexbox.'),
('css-9-3', '9.3 Retour à la Ligne & Gap', 'Configurez flex-wrap: wrap; et gap: 1.5rem;.', 150, 'EASY', '<style>
.flex-wrap { display: flex;
}
</style>', '(?s).*\.flex-wrap\s*\{.*flex-wrap:\s*wrap;?.*gap:\s*1\.5rem;?.*\}.*', 'world-3', '9. La Guilde Flexbox', 'CSS3', 'Flex Wrap & Gap', 'flex-wrap autorise plusieurs lignes, gap gère les gouttières.'),
('css-9-4', '9.4 Propriétés Flex Grow & Shrink', 'Accordez flex-grow: 1; et flex-shrink: 0; à l''élément principal.', 200, 'MEDIUM', '<style>
.main-flex { }
</style>', '(?s).*\.main-flex\s*\{.*flex-grow:\s*1;?.*flex-shrink:\s*0;?.*\}.*', 'world-3', '9. La Guilde Flexbox', 'CSS3', 'Flex Grow & Shrink', 'flex-grow gère l''expansion, flex-shrink gère le rétrécissement.'),
('css-9-5', '9.5 Surcharge Align Self', 'Alignez un seul enfant au bas du conteneur avec align-self: flex-end;.', 200, 'MEDIUM', '<style>
.item-bottom { }
</style>', '(?s).*\.item-bottom\s*\{.*align-self:\s*flex-end;?.*\}.*', 'world-3', '9. La Guilde Flexbox', 'CSS3', 'Align Self', 'align-self surpasse align-items pour un élément spécifique.'),
('css-10-1', '10.1 Grille Multi-colonnes', 'Créez 3 colonnes égales avec display: grid; et grid-template-columns: repeat(3, 1fr);.', 150, 'EASY', '<style>
.grid-3 { }
</style>', '(?s).*\.grid-3\s*\{.*display:\s*grid;?.*grid-template-columns:\s*repeat\(\s*3\s*,\s*1fr\s*\);?.*\}.*', 'world-3', '10. La Matrice Grid', 'CSS3', 'Grid Repeat Columns', 'repeat(3, 1fr) crée 3 colonnes identiques.'),
('css-10-2', '10.2 Espacement Grid Gap', 'Ajoutez un gap de 20px entre les cellules de la grille avec gap: 20px;.', 150, 'EASY', '<style>
.grid-gap { display: grid;
}
</style>', '(?s).*\.grid-gap\s*\{.*gap:\s*20px;?.*\}.*', 'world-3', '10. La Matrice Grid', 'CSS3', 'Grid Gap', 'gap définit l''espace entre rangées et colonnes dans une grille.'),
('css-10-3', '10.3 Placement sur Lignes (grid-column)', 'Étendez la carte sur 2 colonnes avec grid-column: span 2;.', 200, 'MEDIUM', '<style>
.span-2 { }
</style>', '(?s).*\.span-2\s*\{.*grid-column:\s*span\s+2;?.*\}.*', 'world-3', '10. La Matrice Grid', 'CSS3', 'Grid Column Span', 'grid-column: span 2 fait s''étaler une cellule sur 2 colonnes.'),
('css-10-4', '10.4 Template Areas Nommées', 'Définissez grid-template-areas: "header header" "sidebar content";.', 200, 'MEDIUM', '<style>
.layout { display: grid;
}
</style>', '(?s).*\.layout\s*\{.*grid-template-areas:\s*[''\"]header\s+header[''\"]\s+[''\"]sidebar\s+content[''\"];?.*\}.*', 'world-3', '10. La Matrice Grid', 'CSS3', 'Grid Template Areas', 'Les zones nommées créent des mises en page très visuelles.'),
('css-10-5', '10.5 Auto-fit Responsive Minmax', 'Stylez grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));.', 250, 'MEDIUM', '<style>
.auto-grid { display: grid;
}
</style>', '(?s).*\.auto-grid\s*\{.*grid-template-columns:\s*repeat\(\s*auto-fit\s*,\s*minmax\(\s*250px\s*,\s*1fr\s*\)\s*\);?.*\}.*', 'world-3', '10. La Matrice Grid', 'CSS3', 'Auto-fit Minmax Grid', 'Layout 100% responsive et automatique sans media queries.'),
('css-11-1', '11.1 Position Relative', 'Décalez l''élément de 10px vers le bas sans détruire son emplacement d''origine avec position: relative; top: 10px;.', 150, 'EASY', '<style>
.shift { }
</style>', '(?s).*\.shift\s*\{.*position:\s*relative;?.*top:\s*10px;?.*\}.*', 'world-3', '11. Le Royaume du Positionnement', 'CSS3', 'Relative Position', 'relative ajuste visuellement par rapport à sa position normale.'),
('css-11-2', '11.2 Position Absolute & Inset', 'Positionnez le badge en haut à droite avec position: absolute; top: 0; right: 0;.', 150, 'EASY', '<style>
.badge-abs { }
</style>', '(?s).*\.badge-abs\s*\{.*position:\s*absolute;?.*top:\s*0;?.*right:\s*0;?.*\}.*', 'world-3', '11. Le Royaume du Positionnement', 'CSS3', 'Absolute Position', 'absolute sort l''élément du flux et le place par rapport au conteneur.'),
('css-11-3', '11.3 Position Sticky', 'Rendez l''en-tête collant avec position: sticky; top: 0; z-index: 100;.', 150, 'EASY', '<style>
.nav-sticky { }
</style>', '(?s).*\.nav-sticky\s*\{.*position:\s*sticky;?.*top:\s*0;?.*z-index:\s*100;?.*\}.*', 'world-3', '11. Le Royaume du Positionnement', 'CSS3', 'Sticky Position', 'sticky reste relatif puis se fixe lors du défilement.'),
('css-11-4', '11.4 Ordre d''Empilement Z-Index', 'Positionnez le modal au-dessus de l''arrière-plan avec z-index: 9999;.', 150, 'EASY', '<style>
.modal { position: fixed;
}
</style>', '(?s).*\.modal\s*\{.*z-index:\s*9999;?.*\}.*', 'world-3', '11. Le Royaume du Positionnement', 'CSS3', 'Z-Index Stacking', 'z-index définit la priorité d''affichage sur l''axe 3D (Z).'),
('css-12-1', '12.1 Ombres de Boîte (box-shadow)', 'Appliquez box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);.', 150, 'EASY', '<style>
.card-shadow { }
</style>', '(?s).*\.card-shadow\s*\{.*box-shadow:.*0.*10px.*rgba.*\}', 'world-3', '12. Les Ombres & Effets', 'CSS3', 'Box Shadows', 'box-shadow donne du relief et du volume aux cartes.'),
('css-12-2', '12.2 Ombres de Texte (text-shadow)', 'Ajoutez un effet lumineux au titre avec text-shadow: 0 2px 4px rgba(0,0,0,0.5);.', 150, 'EASY', '<style>
h1 { }
</style>', '(?s).*h1\s*\{.*text-shadow:.*0.*2px.*4px.*\}', 'world-3', '12. Les Ombres & Effets', 'CSS3', 'Text Shadows', 'text-shadow améliore le contraste des titres sur images.'),
('css-12-3', '12.3 Filtre Flou CSS (filter: blur)', 'Floutez l''image de fond avec filter: blur(5px);.', 150, 'EASY', '<style>
.bg-blur { }
</style>', '(?s).*\.bg-blur\s*\{.*filter:\s*blur\(\s*5px\s*\);?.*\}.*', 'world-3', '12. Les Ombres & Effets', 'CSS3', 'CSS Filter Blur', 'filter applique des effets graphiques (flou, niveaux de gris).'),
('css-12-4', '12.4 Découpe Polyédrique (clip-path)', 'Découpez un bouton en losange/octogone avec clip-path: circle(50%);.', 200, 'MEDIUM', '<style>
.round-clip { }
</style>', '(?s).*\.round-clip\s*\{.*clip-path:\s*circle\(\s*50%\s*\);?.*\}.*', 'world-3', '12. Les Ombres & Effets', 'CSS3', 'Clip Path Masking', 'clip-path découpe des formes géométriques personnalisées.'),
('css-13-1', '13.1 Mouvements 2D (translate & scale)', 'Stylez transform: translate(10px, -5px) scale(1.05); au survol de .card:hover.', 150, 'EASY', '<style>
.card:hover { }
</style>', '(?s).*\.card:hover\s*\{.*transform:\s*translate\(\s*10px\s*,\s*-5px\s*\)\s+scale\(\s*1\.05\s*\);?.*\}.*', 'world-3', '13. Les Transformations', 'CSS3', '2D Transformations', 'Translate décale et Scale redimensionne sans reflow.'),
('css-13-2', '13.2 Rotation 2D (rotate)', 'Faites pivoter l''icône de 45 degrés avec transform: rotate(45deg);.', 150, 'EASY', '<style>
.icon-rotate { }
</style>', '(?s).*\.icon-rotate\s*\{.*transform:\s*rotate\(\s*45deg\s*\);?.*\}.*', 'world-3', '13. Les Transformations', 'CSS3', 'Rotate Transformation', 'rotate fait tourner les éléments en degrés.'),
('css-13-3', '13.3 Origine de Transformation (transform-origin)', 'Changez le point de pivot au coin supérieur gauche avec transform-origin: top left;.', 150, 'EASY', '<style>
.pivot { }
</style>', '(?s).*\.pivot\s*\{.*transform-origin:\s*top\s+left;?.*\}.*', 'world-3', '13. Les Transformations', 'CSS3', 'Transform Origin', 'transform-origin déplace le point d''ancrage des rotations.'),
('css-14-1', '14.1 Transition Raccourcie', 'Ajoutez transition: all 0.3s ease-in-out; au bouton.', 150, 'EASY', '<style>
.btn { }
</style>', '(?s).*\.btn\s*\{.*transition:\s*all\s+0\.3s\s+ease-in-out;?.*\}.*', 'world-3', '14. Les Enchantements Animés', 'CSS3', 'CSS Transition Shortcut', 'transition fluidifie les changements de propriétés CSS.'),
('css-14-2', '14.2 Keyframes de Fondu (Fade-In)', 'Définissez @keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }.', 200, 'MEDIUM', '<style>
</style>', '(?s).*@keyframes\s+fadeIn\s*\{.*from\s*\{.*opacity:\s*0;?.*\}.*to\s*\{.*opacity:\s*1;?.*\}.*\}.*', 'world-3', '14. Les Enchantements Animés', 'CSS3', 'Fade In Keyframes', '@keyframes définit la logique temporelle de l''animation.'),
('css-14-3', '14.3 Propriétés d''Animation', 'Appliquez animation: fadeIn 1s ease-out forwards; à .banner.', 150, 'EASY', '<style>
.banner { }
</style>', '(?s).*\.banner\s*\{.*animation:\s*fadeIn\s+1s\s+ease-out\s+forwards;?.*\}.*', 'world-3', '14. Les Enchantements Animés', 'CSS3', 'Animation Shorthand', 'animation applique la directive @keyframes avec sa durée et sa vitesse.'),
('css-14-4', '14.4 Respect du Mouvement Réduit', 'Désactivez les animations avec @media (prefers-reduced-motion: reduce) { * { animation: none !important; } }.', 200, 'MEDIUM', '<style>
</style>', '(?s).*@media\s*\(\s*prefers-reduced-motion:\s*reduce\s*\)\s*\{\s*\*\s*\{\s*animation:\s*none\s*!important;?\s*\}\s*\}.*', 'world-3', '14. Les Enchantements Animés', 'CSS3', 'Reduced Motion A11y', 'Respecter prefers-reduced-motion protège les utilisateurs sensibles.'),
('css-15-1', '15.1 Pseudo-Classes d''État (:hover & :active)', 'Appliquez opacity: 0.8; au :hover et transform: scale(0.98); au :active sur .btn-state.', 150, 'EASY', '<style>
.btn-state:hover { }
.btn-state:active { }
</style>', '(?s).*\.btn-state:hover\s*\{.*opacity:\s*0\.8;?.*\}.*\.btn-state:active\s*\{.*transform:\s*scale\(\s*0\.98\s*\);?.*\}.*', 'world-3', '15. Les Pseudo-Classes & Pseudo-Éléments', 'CSS3', 'Hover & Active States', ':hover réagit au survol et :active au clic enfoncé.'),
('css-15-2', '15.2 Sélecteur de Premier et Dernier Enfant', 'Ciblez li:first-child (font-weight: bold;) et li:last-child (border-bottom: none;).', 150, 'EASY', '<style>
li:first-child { }
li:last-child { }
</style>', '(?s).*li:first-child\s*\{.*font-weight:\s*bold;?.*\}.*li:last-child\s*\{.*border-bottom:\s*none;?.*\}.*', 'world-3', '15. Les Pseudo-Classes & Pseudo-Éléments', 'CSS3', 'First & Last Child', ':first-child et :last-child ciblent les extrémités d''une liste.'),
('css-15-3', '15.3 Pseudo-Classe d''Exclusion (:not)', 'Ciblez tous les boutons sauf le primaire avec button:not(.btn-primary) { background: transparent; }.', 150, 'EASY', '<style>
button:not(.btn-primary) { }
</style>', '(?s).*button:not\(\s*\.btn-primary\s*\)\s*\{.*background:\s*transparent;?.*\}.*', 'world-3', '15. Les Pseudo-Classes & Pseudo-Éléments', 'CSS3', 'Negation Pseudo-Class :not', ':not() exclut certains éléments ciblés par la règle.'),
('css-15-4', '15.4 Pseudo-Éléments ::before & ::after', 'Ajoutez une flèche après les liens externes avec .external::after { content: '' ↗''; }.', 150, 'EASY', '<style>
.external::after { }
</style>', '(?s).*\.external::after\s*\{.*content:\s*[''\"] ↗[''\"];?.*\}.*', 'world-3', '15. Les Pseudo-Classes & Pseudo-Éléments', 'CSS3', 'Pseudo-element ::after', '::after insère du contenu décoratif à la fin de l''élément.'),
('css-15-5', '15.5 Stylisation de la Sélection Textuelle (::selection)', 'Personnalisez la couleur de surbrillance avec ::selection { background: #8b5cf6; color: #fff; }.', 150, 'EASY', '<style>
::selection { }
</style>', '(?s).*::selection\s*\{.*background:\s*#8b5cf6;?.*color:\s*#fff;?.*\}.*', 'world-3', '15. Les Pseudo-Classes & Pseudo-Éléments', 'CSS3', 'Selection Styling', '::selection personnalise le texte surligné à la souris.'),
('css-16-1', '16.1 Déclaration & Utilisation de Variables', 'Définissez --primary-color: #3b82f6; sur :root et réutilisez-la avec var(--primary-color).', 150, 'EASY', '<style>
:root { }
.text-primary { }
</style>', '(?s).*:root\s*\{.*--primary-color:\s*#3b82f6;?.*\}.*\.text-primary\s*\{.*color:\s*var\(\s*--primary-color\s*\);?.*\}.*', 'world-3', '16. Les Variables CSS', 'CSS3', 'CSS Custom Properties', 'Les variables CSS s''héritent dynamiquement dans le DOM.'),
('css-16-2', '16.2 Valeur de Secours (Fallback Var)', 'Fournissez une valeur de fallback par défaut avec color: var(--text-main, #1e293b);.', 150, 'EASY', '<style>
.text-safe { }
</style>', '(?s).*\.text-safe\s*\{.*color:\s*var\(\s*--text-main\s*,\s*#1e293b\s*\);?.*\}.*', 'world-3', '16. Les Variables CSS', 'CSS3', 'Variable Fallbacks', 'Le second argument de var() sert de secours si la variable n''existe pas.'),
('css-16-3', '16.3 Variables Locales de Composant', 'Surchargez la variable locale sur le conteneur .card-dark { --card-bg: #1e1b4b; }.', 150, 'EASY', '<style>
.card-dark { }
</style>', '(?s).*\.card-dark\s*\{.*--card-bg:\s*#1e1b4b;?.*\}.*', 'world-3', '16. Les Variables CSS', 'CSS3', 'Local Scoped Variables', 'Redéfinir une variable localement surplante sa valeur globale pour ce composant.'),
('css-17-1', '17.1 Media Query Mobile-First', 'Passez en flex-direction: row; uniquement au-dessus de 768px (@media (min-width: 768px)).', 200, 'MEDIUM', '<style>
.container { display: flex; flex-direction: column; }
</style>', '(?s).*@media\s*\(\s*min-width:\s*768px\s*\)\s*\{\s*\.container\s*\{\s*flex-direction:\s*row;?\s*\}\s*\}.*', 'world-3', '17. La Forteresse Responsive', 'CSS3', 'Mobile First Breakpoint', 'L''approche Mobile-First commence sur petit écran sans media query.'),
('css-17-2', '17.2 Orientation d''Écran', 'Appliquez des styles spécifiques au mode paysage avec @media (orientation: landscape) { .header { height: 60px; } }.', 150, 'EASY', '<style>
</style>', '(?s).*@media\s*\(\s*orientation:\s*landscape\s*\)\s*\{\s*\.header\s*\{\s*height:\s*60px;?\s*\}\s*\}.*', 'world-3', '17. La Forteresse Responsive', 'CSS3', 'Orientation Media Query', 'orientation: landscape cible les écrans plus larges que hauts.'),
('css-17-3', '17.3 Mode Sombre Système', 'Surchargez le fond en mode sombre avec @media (prefers-color-scheme: dark) { body { background: #0f172a; } }.', 200, 'MEDIUM', '<style>
</style>', '(?s).*@media\s*\(\s*prefers-color-scheme:\s*dark\s*\)\s*\{\s*body\s*\{\s*background:\s*#0f172a;?\s*\}\s*\}.*', 'world-3', '17. La Forteresse Responsive', 'CSS3', 'Prefers Color Scheme Dark', 'Adapte les couleurs au thème système de l''appareil.'),
('css-17-4', '17.4 Container Queries (@container)', 'Définissez container-type: inline-size; sur le conteneur parent .card-container.', 200, 'MEDIUM', '<style>
.card-container { }
</style>', '(?s).*\.card-container\s*\{.*container-type:\s*inline-size;?.*\}.*', 'world-3', '17. La Forteresse Responsive', 'CSS3', 'Container Queries Base', 'Les Container Queries adaptent les styles à la taille du parent.'),
('css-18-1', '18.1 Arrière-plan Couvrant (background-size: cover)', 'Appliquez background-size: cover; et background-position: center;.', 150, 'EASY', '<style>
.hero-bg { }
</style>', '(?s).*\.hero-bg\s*\{.*background-size:\s*cover;?.*background-position:\s*center;?.*\}.*', 'world-3', '18. Le Sanctuaire des Images & Arrière-plans', 'CSS3', 'Background Cover', 'background-size: cover adapte l''image pour remplir tout le conteneur.'),
('css-18-2', '18.2 Redimensionnement d''Images Sémantiques (object-fit)', 'Empêchez la distorsion des images avec object-fit: cover; et object-position: center;.', 150, 'EASY', '<style>
img.avatar { }
</style>', '(?s).*img\.avatar\s*\{.*object-fit:\s*cover;?.*object-position:\s*center;?.*\}.*', 'world-3', '18. Le Sanctuaire des Images & Arrière-plans', 'CSS3', 'Object Fit Cover', 'object-fit contrôle l''ajustement des éléments <img> et <video>.'),
('css-18-3', '18.3 Ratio de Dimensions Parfait (aspect-ratio)', 'Maintenez un ratio vidéo 16/9 avec aspect-ratio: 16 / 9;.', 150, 'EASY', '<style>
.video-frame { }
</style>', '(?s).*\.video-frame\s*\{.*aspect-ratio:\s*16\s*/\s*9;?.*\}.*', 'world-3', '18. Le Sanctuaire des Images & Arrière-plans', 'CSS3', 'Aspect Ratio', 'aspect-ratio bloque le rapport largeur/hauteur d''une boîte.'),
('css-19-1', '19.1 Contour de Focus Clavier Explicite', 'Configurez .btn-a11y:focus-visible { outline: 3px solid #3b82f6; outline-offset: 2px; }.', 150, 'EASY', '<style>
.btn-a11y:focus-visible { }
</style>', '(?s).*\.btn-a11y:focus-visible\s*\{.*outline:\s*3px\s+solid\s+#3b82f6;?.*outline-offset:\s*2px;?.*\}.*', 'world-3', '19. L''Armure d''Accessibilité', 'CSS3', 'Focus Visible Styling', 'Ne masquez jamais les contours sans alternative accessible :focus-visible.'),
('css-19-2', '19.2 Masquage Accessible Screen Reader (.sr-only)', 'Créez une classe .sr-only avec position: absolute; width: 1px; height: 1px; overflow: hidden; clip: rect(0,0,0,0);.', 200, 'MEDIUM', '<style>
.sr-only { }
</style>', '(?s).*\.sr-only\s*\{.*position:\s*absolute;?.*width:\s*1px;?.*height:\s*1px;?.*overflow:\s*hidden;?.*clip:\s*rect.*\}', 'world-3', '19. L''Armure d''Accessibilité', 'CSS3', 'Screen Reader Only Class', 'sr-only rend le texte lisible pour la synthèse vocale sans l''afficher à l''écran.'),
('css-19-3', '19.3 Mode de Fort Contraste System', 'Prenez en compte prefers-contrast avec @media (prefers-contrast: more) { .border-high { border-width: 3px; } }.', 150, 'EASY', '<style>
</style>', '(?s).*@media\s*\(\s*prefers-contrast:\s*more\s*\)\s*\{\s*\.border-high\s*\{\s*border-width:\s*3px;?\s*\}\s*\}.*', 'world-3', '19. L''Armure d''Accessibilité', 'CSS3', 'Prefers High Contrast', 'S''adapte aux utilisateurs nécessitant un contraste visuel renforcé.'),
('css-20-1', '20.1 États Valides & Invalides de Champs', 'Stylez input:valid { border-color: #10b981; } et input:invalid { border-color: #ef4444; }.', 150, 'EASY', '<style>
input:valid { }
input:invalid { }
</style>', '(?s).*input:valid\s*\{.*border-color:\s*#10b981;?.*\}.*input:invalid\s*\{.*border-color:\s*#ef4444;?.*\}.*', 'world-3', '20. Le Codex des Formulaires', 'CSS3', 'Input Validation Pseudo-classes', 'Feedback visuel instantané de saisie utilisateur.'),
('css-20-2', '20.2 Détection de Placeholder Affiché', 'Cachez le label flottant quand le placeholder est visible avec input:placeholder-shown + label { opacity: 0; }.', 200, 'MEDIUM', '<style>
input:placeholder-shown + label { }
</style>', '(?s).*input:placeholder-shown\s*\+\s*label\s*\{.*opacity:\s*0;?.*\}.*', 'world-3', '20. Le Codex des Formulaires', 'CSS3', 'Placeholder Shown State', ':placeholder-shown détecte si le champ est vide.'),
('css-20-3', '20.3 Personnalisation de Checkbox Native', 'Utilisez accent-color: #8b5cf6; pour teinter une case à cocher native.', 150, 'EASY', '<style>
input[type="checkbox"] { }
</style>', '(?s).*input\[type=\"checkbox\"\]\s*\{.*accent-color:\s*#8b5cf6;?.*\}.*', 'world-3', '20. Le Codex des Formulaires', 'CSS3', 'Accent Color Styling', 'accent-color personnalise la couleur des contrôles natifs du navigateur.'),
('css-21-1', '21.1 Convention BEM (Block Element Modifier)', 'Appliquez la classe BEM modifiée .btn--secondary { background: gray; }.', 150, 'EASY', '<style>
.btn--secondary { }
</style>', '(?s).*\.btn--secondary\s*\{.*background:\s*gray;?.*\}.*', 'world-3', '21. L''Architecture CSS', 'CSS3', 'BEM Naming Convention', 'BEM (Block__Element--Modifier) prévient les conflits de nommage CSS.'),
('css-21-2', '21.2 Déclaration de Cascading Layers (@layer)', 'Définissez l''ordre de priorité des couches avec @layer reset, components, utilities;.', 200, 'MEDIUM', '<style>
</style>', '(?s).*@layer\s+reset\s*,\s*components\s*,\s*utilities;.*', 'world-3', '21. L''Architecture CSS', 'CSS3', 'Cascade Layers Priority', '@layer définit une hiérarchie stricte d''application du style.'),
('css-21-3', '21.3 Attribution dans une Couche Spécifique', 'Encapsulez la règle dans la couche components avec @layer components { .card { padding: 1rem; } }.', 150, 'EASY', '<style>
</style>', '(?s).*@layer\s+components\s*\{\s*\.card\s*\{\s*padding:\s*1rem;?\s*\}\s*\}.*', 'world-3', '21. L''Architecture CSS', 'CSS3', 'Layer Enclosure', 'Placer une règle dans une @layer la protège des surcharges accidentelles.'),
('css-22-1', '22.1 Imbrication CSS Natif (CSS Nesting)', 'Imbriquez le sélecteur de survol .card { &:hover { transform: scale(1.02); } }.', 200, 'MEDIUM', '<style>
.card {
}
</style>', '(?s).*\.card\s*\{.*&\s*:hover\s*\{\s*transform:\s*scale\(\s*1\.02\s*\);?\s*\}\s*\}.*', 'world-3', '22. Le CSS Moderne', 'CSS3', 'Native CSS Nesting', 'Le nesting CSS natif élimine le besoin de préprocesseurs Sass.'),
('css-22-2', '22.2 Propriétés Logiques (margin-block & padding-inline)', 'Remplacez margin-top/bottom par margin-block: 1rem; et padding-left/right par padding-inline: 2rem;.', 200, 'MEDIUM', '<style>
.box-logical { }
</style>', '(?s).*\.box-logical\s*\{.*margin-block:\s*1rem;?.*padding-inline:\s*2rem;?.*\}.*', 'world-3', '22. Le CSS Moderne', 'CSS3', 'Logical Box Properties', 'Les propriétés logiques s''adaptent à la direction d''écriture (LTR/RTL).'),
('css-22-3', '22.3 Mélange de Couleurs avec color-mix()', 'Mélangez 50% de bleu et 50% de blanc avec background: color-mix(in srgb, blue 50%, white);.', 250, 'MEDIUM', '<style>
.mixed-color { }
</style>', '(?s).*\.mixed-color\s*\{.*background:\s*color-mix\(\s*in\s+srgb\s*,\s*blue\s+50%\s*,\s*white\s*\);?.*\}.*', 'world-3', '22. Le CSS Moderne', 'CSS3', 'Color Mix Function', 'color-mix() combine deux teintes directement en CSS.'),
('css-23-1', '23.1 Optimisation GPU (will-change)', 'Préparez l''accélération matérielle avec will-change: transform, opacity;.', 150, 'EASY', '<style>
.fast-anim { }
</style>', '(?s).*\.fast-anim\s*\{.*will-change:\s*transform\s*,\s*opacity;?.*\}.*', 'world-3', '23. Performance & Rendu', 'CSS3', 'GPU Acceleration', 'will-change indique les propriétés animées au moteur de composition GPU.'),
('css-23-2', '23.2 Isolation d''Affichage avec contain', 'Isolez les calculs de disposition d''un sous-arbre avec contain: layout paint;.', 200, 'MEDIUM', '<style>
.widget-isolated { }
</style>', '(?s).*\.widget-isolated\s*\{.*contain:\s*layout\s+paint;?.*\}.*', 'world-3', '23. Performance & Rendu', 'CSS3', 'CSS Containment', 'contain limite la portée des reflows et repaints.'),
('css-24-1', '24.1 Outlines de Débogage Universels', 'Affichez un contour de débogage rouge 1px sur tous les éléments (* { outline: 1px solid red; }).', 100, 'EASY', '<style>
</style>', '(?s).*\*\s*\{.*outline:\s*1px\s+solid\s+red;?.*\}.*', 'world-3', '24. Qualité & Débogage', 'CSS3', 'Debug Layout Outline', 'outline permet d''inspecter les limites de boîte sans modifier leurs dimensions.'),
('css-24-2', '24.2 Désactivation de Sélection Involontaire', 'Empêchez la sélection de texte sur un bouton d''action avec user-select: none;.', 100, 'EASY', '<style>
.no-select { }
</style>', '(?s).*\.no-select\s*\{.*user-select:\s*none;?.*\}.*', 'world-3', '24. Qualité & Débogage', 'CSS3', 'User Select Control', 'user-select: none empêche le surlignage accidentel lors de double-clics rapides.'),
('css-25-1', '25.1 Bouton Lumineux Néon (Glowing Neon Button)', 'Créez un bouton style néon avec box-shadow: 0 0 15px #8b5cf6, 0 0 30px #8b5cf6; et color: #fff;.', 200, 'MEDIUM', '<style>
.btn-neon { background: #0f172a;
}
</style>
<button class="btn-neon">NÉON</button>', '(?s).*\.btn-neon\s*\{.*box-shadow:.*0.*0.*15px.*#8b5cf6.*0.*0.*30px.*#8b5cf6;?.*color:\s*#fff;?.*\}.*', 'world-3', '25. ✨ Quêtes Annexes CSS3', 'CSS3', 'Neon Glowing Effect', 'Effet néon lumineux combinant plusieurs couches d''ombre box-shadow.'),
('css-25-2', '25.2 Carte 3D Flip sur Survol', 'Activez la perspective 3D sur le conteneur parent avec perspective: 1000px;.', 200, 'MEDIUM', '<style>
.card-flip-container { }
</style>
<div class="card-flip-container"><div class="card-inner"></div></div>', '(?s).*\.card-flip-container\s*\{.*perspective:\s*1000px;?.*\}.*', 'world-3', '25. ✨ Quêtes Annexes CSS3', 'CSS3', '3D Card Flip Perspective', 'perspective définit la distance visuelle 3D pour la transformation des enfants.'),
('css-25-3', '25.3 Indicateur de Chargement Réactif (Custom Ring Spinner)', 'Créez une bordure circulaire avec border: 4px solid #e2e8f0; et border-top-color: #3b82f6;.', 150, 'EASY', '<style>
.ring-spinner {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
</style>', '(?s).*\.ring-spinner\s*\{.*border:\s*4px\s+solid\s+#e2e8f0;?.*border-top-color:\s*#3b82f6;?.*\}.*', 'world-3', '25. ✨ Quêtes Annexes CSS3', 'CSS3', 'Ring Loading Spinner', 'Un spinner propre et élégant combinant border et border-top-color.'),
('css-25-4', '25.4 Info-bulle CSS Uniquement (Tooltip pure CSS)', 'Positionnez une info-bulle en haut du bouton au survol avec .tooltip:hover::after { content: attr(data-tooltip); position: absolute; top: -30px; }.', 250, 'MEDIUM', '<style>
.tooltip {
  position: relative;
}
/* Configurez .tooltip:hover::after */

</style>
<button class="tooltip" data-tooltip="Enregistrer les modifications">Sauvegarder</button>', '(?s).*\.tooltip:hover::after\s*\{.*content:\s*attr\(\s*data-tooltip\s*\);?.*position:\s*absolute;?.*top:\s*-30px;?.*\}.*', 'world-3', '25. ✨ Quêtes Annexes CSS3', 'CSS3', 'Pure CSS Tooltip', 'Affiche un tooltip dynamique tiré de data-tooltip sans une seule ligne de JavaScript.'),
('css-25-5', '25.5 Badge Ruban d''Angle (Corner Ribbon Badge)', 'Positionnez un ruban promo en haut à droite avec position: absolute; top: 10px; right: -20px; transform: rotate(45deg);.', 200, 'MEDIUM', '<style>
.corner-ribbon { }
</style>', '(?s).*\.corner-ribbon\s*\{.*position:\s*absolute;?.*top:\s*10px;?.*right:\s*-20px;?.*transform:\s*rotate\(\s*45deg\s*\);?.*\}.*', 'world-3', '25. ✨ Quêtes Annexes CSS3', 'CSS3', 'Corner Ribbon Badge', 'Un badge d''angle stylé pour les cartes de produits ou remises.'),
('css-25-6', '25.6 Interrupteur Toggle Switch en pure CSS', 'Concevez le rail de l''interrupteur avec width: 50px;, height: 26px;, border-radius: 13px; et background-color: #cbd5e1;.', 200, 'MEDIUM', '<style>
.toggle-track { }
</style>', '(?s).*\.toggle-track\s*\{.*width:\s*50px;?.*height:\s*26px;?.*border-radius:\s*13px;?.*background-color:\s*#cbd5e1;?.*\}.*', 'world-3', '25. ✨ Quêtes Annexes CSS3', 'CSS3', 'Custom CSS Toggle Switch', 'Le rail de base d''un interrupteur à bascule ergonomique.'),
('css-25-7', '25.7 Effet de Squelette de Chargement (Skeleton Pulse)', 'Créez une animation de squelette de chargement avec @keyframes skeleton { 0%, 100% { opacity: 0.4; } 50% { opacity: 0.8; } }.', 200, 'MEDIUM', '<style>
/* Définissez @keyframes skeleton */

</style>', '(?s).*@keyframes\s+skeleton\s*\{.*0%\s*,\s*100%\s*\{\s*opacity:\s*0\.4;?\s*\}\s*.*50%\s*\{\s*opacity:\s*0\.8;?\s*\}\s*\}.*', 'world-3', '25. ✨ Quêtes Annexes CSS3', 'CSS3', 'Skeleton Loader Pulse', 'Une animation de pulsation fluide pour les composants en attente de données API.'),
('css-25-8', '25.8 Titre avec Texte à Gradient de Couleur', 'Appliquez un dégradé sur le texte du titre avec background: linear-gradient(90deg, #3b82f6, #ec4899); -webkit-background-clip: text; color: transparent;.', 250, 'MEDIUM', '<style>
.gradient-text { }
</style>
<h1 class="gradient-text">TEXTE MAGIQUE</h1>', '(?s).*\.gradient-text\s*\{.*background:\s*linear-gradient\(\s*90deg\s*,\s*#3b82f6\s*,\s*#ec4899\s*\);?.*-webkit-background-clip:\s*text;?.*color:\s*transparent;?.*\}.*', 'world-3', '25. ✨ Quêtes Annexes CSS3', 'CSS3', 'Gradient Text Masking', 'Magie visuelle moderne permettant d''appliquer un dégradé directement sur la typographie.'),
('css-25-9', '25.9 Accordéon CSS Pure sans JavaScript', 'Dépliez le panneau d''accordéon lorsque la case cachée est cochée avec .accordion-toggle:checked ~ .accordion-body { max-height: 200px; }.', 250, 'MEDIUM', '<style>
.accordion-body {
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.3s ease;
}
/* Définissez la règle :checked */

</style>
<input type="checkbox" class="accordion-toggle" id="acc1">
<div class="accordion-body">Contenu dépliable</div>', '(?s).*\.accordion-toggle:checked\s*~\s*\.accordion-body\s*\{.*max-height:\s*200px;?.*\}.*', 'world-3', '25. ✨ Quêtes Annexes CSS3', 'CSS3', 'Pure CSS Accordion', 'Pattern d''accordéon dépliable 100% CSS basé sur la pseudo-classe :checked.'),
('css-25-10', '25.10 Effet de Brillance sur Survol (Shine Button)', 'Créez un reflet brillant balayant le bouton avec background: linear-gradient(120deg, transparent 30%, rgba(255,255,255,0.4) 50%, transparent 70%);.', 250, 'MEDIUM', '<style>
.btn-shine::after { }
</style>', '(?s).*\.btn-shine::after\s*\{.*background:\s*linear-gradient\(\s*120deg\s*,\s*transparent\s+30%\s*,\s*rgba\(\s*255\s*,\s*255\s*,\s*255\s*,\s*0\.4\s*\)\s+50%\s*,\s*transparent\s+70%\s*\);?.*\}.*', 'world-3', '25. ✨ Quêtes Annexes CSS3', 'CSS3', 'Shine Hover Effect', 'Animation de balayage lumineux pour sublimer les boutons d''appel à l''action.'),
('css-26-1', '26.1 Quiz 1 : Syntaxe, Sélecteurs & Specificité', 'Testez vos connaissances en 3 questions sur les sélecteurs et la cascade CSS.', 200, 'EASY', '<!-- Q1: Quel sélecteur a la plus haute spécificité ? A: .classe | B: #id | C: div | D: * -->
<question id="1" data-reponse=""></question>
<!-- Q2: Quel combinateur cible les enfants directs ? A: espace | B: > | C: + | D: ~ -->
<question id="2" data-reponse=""></question>
<!-- Q3: Quelle règle réinitialise à la valeur héritée ? A: reset | B: initial | C: unset | D: clear -->
<question id="3" data-reponse=""></question>', '(?s).*<question\s+id=\"1\"\s+data-reponse=\"B\"\s*></question\s*>.*<question\s+id=\"2\"\s+data-reponse=\"B\"\s*></question\s*>.*<question\s+id=\"3\"\s+data-reponse=\"C\"\s*></question\s*>.*', 'world-3', '26. 🎓 Quiz & Évaluation des Connaissances CSS3', 'Quiz, CSS3', 'Quiz CSS 1', 'Validation du premier module théorique CSS3.'),
('css-26-2', '26.2 Quiz 2 : Box Model, Couleurs & Typographie', 'Testez vos connaissances en 3 questions sur le box model et les dégradés.', 200, 'EASY', '<!-- Q1: Quelle propriété inclut padding dans width ? A: box-sizing: content-box | B: box-sizing: border-box | C: width-box | D: margin-box -->
<question id="1" data-reponse=""></question>
<!-- Q2: Quelle unité est relative à la racine <html> ? A: em | B: rem | C: vh | D: px -->
<question id="2" data-reponse=""></question>
<!-- Q3: Quelle fonction limite une valeur entre min et max ? A: min() | B: clamp() | C: max() | D: fit() -->
<question id="3" data-reponse=""></question>', '(?s).*<question\s+id=\"1\"\s+data-reponse=\"B\"\s*></question\s*>.*<question\s+id=\"2\"\s+data-reponse=\"B\"\s*></question\s*>.*<question\s+id=\"3\"\s+data-reponse=\"B\"\s*></question\s*>.*', 'world-3', '26. 🎓 Quiz & Évaluation des Connaissances CSS3', 'Quiz, CSS3', 'Quiz CSS 2', 'Validation du deuxième module théorique CSS3.'),
('css-26-3', '26.3 Quiz 3 : Flexbox, Grid & Positionnement', 'Testez vos connaissances en 3 questions sur la mise en page Flexbox et Grid.', 200, 'EASY', '<!-- Q1: Quelle propriété aligne sur l''axe principal Flexbox ? A: align-items | B: justify-content | C: flex-flow | D: gap -->
<question id="1" data-reponse=""></question>
<!-- Q2: Quelle fonction Grid crée des colonnes automatiques ? A: repeat(auto-fit, minmax(...)) | B: grid-auto() | C: flex-grid() | D: fit-columns() -->
<question id="2" data-reponse=""></question>
<!-- Q3: Quelle position ancre un élément au scroll ? A: fixed | B: sticky | C: absolute | D: relative -->
<question id="3" data-reponse=""></question>', '(?s).*<question\s+id=\"1\"\s+data-reponse=\"B\"\s*></question\s*>.*<question\s+id=\"2\"\s+data-reponse=\"A\"\s*></question\s*>.*<question\s+id=\"3\"\s+data-reponse=\"B\"\s*></question\s*>.*', 'world-3', '26. 🎓 Quiz & Évaluation des Connaissances CSS3', 'Quiz, CSS3', 'Quiz CSS 3', 'Validation du troisième module théorique CSS3.'),
('css-26-4', '26.4 Quiz 4 : Transformations, Keyframes & Effets Visuels', 'Testez vos connaissances en 3 questions sur les animations et le glassmorphism.', 200, 'EASY', '<!-- Q1: Quelle directive définit les étapes d''une animation ? A: @transition | B: @keyframes | C: @animate | D: @motion -->
<question id="1" data-reponse=""></question>
<!-- Q2: Quelle propriété applique un flou au fond arrière ? A: filter: blur() | B: backdrop-filter: blur() | C: background-blur | D: opacity -->
<question id="2" data-reponse=""></question>
<!-- Q3: Quelle transformation modifie l''échelle ? A: translate() | B: rotate() | C: scale() | D: skew() -->
<question id="3" data-reponse=""></question>', '(?s).*<question\s+id=\"1\"\s+data-reponse=\"B\"\s*></question\s*>.*<question\s+id=\"2\"\s+data-reponse=\"B\"\s*></question\s*>.*<question\s+id=\"3\"\s+data-reponse=\"C\"\s*></question\s*>.*', 'world-3', '26. 🎓 Quiz & Évaluation des Connaissances CSS3', 'Quiz, CSS3', 'Quiz CSS 4', 'Validation du quatrième module théorique CSS3.'),
('css-26-5', '26.5 Quiz 5 : CSS Moderne, Variables, Responsive & Accessibilité', 'Testez vos connaissances en 3 questions sur le CSS moderne (:has(), :focus-visible).', 250, 'MEDIUM', '<!-- Q1: Quel sélecteur cible un parent selon ses enfants ? A: :is() | B: :has() | C: :where() | D: :parent() -->
<question id="1" data-reponse=""></question>
<!-- Q2: Quelle pseudo-classe affiche le focus uniquement au clavier ? A: :focus | B: :focus-visible | C: :active | D: :hover -->
<question id="2" data-reponse=""></question>
<!-- Q3: Quelle directive déclare des couches d''architecture CSS ? A: @import | B: @layer | C: @media | D: @scope -->
<question id="3" data-reponse=""></question>', '(?s).*<question\s+id=\"1\"\s+data-reponse=\"B\"\s*></question\s*>.*<question\s+id=\"2\"\s+data-reponse=\"B\"\s*></question\s*>.*<question\s+id=\"3\"\s+data-reponse=\"B\"\s*></question\s*>.*', 'world-3', '26. 🎓 Quiz & Évaluation des Connaissances CSS3', 'Quiz, CSS3', 'Quiz CSS 5', 'Validation finale du module CSS3.')
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    description = EXCLUDED.description,
    xp_reward = EXCLUDED.xp_reward,
    difficulty = EXCLUDED.difficulty,
    code_template = EXCLUDED.code_template,
    test_validation_regex = EXCLUDED.test_validation_regex,
    world_id = EXCLUDED.world_id,
    category = EXCLUDED.category,
    languages = EXCLUDED.languages,
    concept = EXCLUDED.concept,
    theory = EXCLUDED.theory;

