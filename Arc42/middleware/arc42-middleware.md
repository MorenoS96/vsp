# 

**Über arc42**

arc42, das Template zur Dokumentation von Software- und
Systemarchitekturen.

Template Version 8.1 DE. (basiert auf AsciiDoc Version), Mai 2022

Created, maintained and © by Dr. Peter Hruschka, Dr. Gernot Starke and
contributors. Siehe <https://arc42.org>.

# Einführung und Ziele {#section-introduction-and-goals}

- Die Middleware soll folgende Ziele erreichen
- Bereitstellen von Funktionen um Applikation miteinander kommunizieren lassen zu können. In unserem in Form von RPC bzw. RMI
- Ermöglichen Ressourcen (CPU, Speicher) effizient teilen und nutzen zu können
- Logging von Zugriffen um Nachrichtenflüsse nachzuvollziehen
- Fehler maskieren und wenn möglich beheben.
- Aufteilung in Transparenz-Ziele, Scalierbarkeit und Offenheit

## Aufgabenstellung {#_aufgabenstellung}

Es sollen alle nötigen Ressourcen so geteilt werden, dass das Spielen unserer Tron Anwendung zusammen mit einer anderen Gruppe möglich ist.
Dafür sollen wir eine Middleware entwickeln, welche es mindestens unseren beiden Gruppen ermöglicht zusammen zu spielen.

## Qualitätsziele {#_qualit_tsziele}

- Transparenz-Ziele (Objekt -> Ressource oder Prozess)
  - Zugriffstransparenz: Der Callee darf nicht wissen, wie auf ein Objekt/eine Funktion zugegriffen wird 
  - Ortstransparenz: Der Callee darf nicht wissen, wo sich ein Objekt (räumlich) befindet 
  - Verlegungstransparenz: Das System schiebt Prozesse und Ressourcen, ohne dass bestehende Kommunikation behindert wird. 
  - Migrationsstransparenz: Prozesse dürfen ihren Standort wechseln, ohne dass bestehende Kommunikation behindert wird. 
  - Replikationstransparenz: Der Callee darf nicht wissen, dass eine Ressource/Prozess mehrmals vorhanden sein kann 
  - Fehlertransparenz: Der Callee darf nicht wissen, dass Fehler auftreten und behandelt werden
- Skalierbarkeit:
  - Größenskalierung: es soll die Kommunikation zwischen mindestens 6 Teilnehmern möglich sein
  - Geografische-Skalierung: Betrieb soll im LAN möglich sein
- Offenheit:
  - Interoperabilität: Es soll Kommunikation mit (mindestens) einer weiteren Gruppe möglich sein
  - Portabilität: Die Middleware kann ausgetauscht werden, solange die Interfaces zusammenpassen
  - Erweiterbarkeit: Es sollte möglich sein, Komponenten zur Middleware hinzuzufügen oder ggf. welche auszutauschen

## Stakeholder {#_stakeholder}

| Rolle         | Kontakt         | Erwartungshaltung |
|---------------|-----------------|-------------------|
| *\<Rolle-1\>* | *\<Kontakt-1\>* | *\<Erwartung-1\>* |
| *\<Rolle-2\>* | *\<Kontakt-2\>* | *\<Erwartung-2\>* |

# Randbedingungen {#section-architecture-constraints}

- Objekt-orientierte Sprache wird zur Umsetzung genutzt (Java)
- Der Server darf von Teilnehmern nicht blockiert werden
- Muss im LAN in Raum 07.65 laufen
- Nutzung von RPC oder RMI

# Kontextabgrenzung {#section-system-scope-and-context}

## Fachlicher Kontext {#_fachlicher_kontext}

**\<Diagramm und/oder Tabelle\>**

**\<optional: Erläuterung der externen fachlichen Schnittstellen\>**

## Technischer Kontext {#_technischer_kontext}

**\<Diagramm oder Tabelle\>**

**\<optional: Erläuterung der externen technischen Schnittstellen\>**

**\<Mapping fachliche auf technische Schnittstellen\>**

# Lösungsstrategie {#section-solution-strategy}

# Bausteinsicht {#section-building-block-view}

## Whitebox Gesamtsystem {#_whitebox_gesamtsystem}

***\<Übersichtsdiagramm\>***

Begründung

:   *\<Erläuternder Text\>*

Enthaltene Bausteine

:   *\<Beschreibung der enthaltenen Bausteine (Blackboxen)\>*

Wichtige Schnittstellen

:   *\<Beschreibung wichtiger Schnittstellen\>*

### \<Name Blackbox 1\> {#__name_blackbox_1}

*\<Zweck/Verantwortung\>*

*\<Schnittstelle(n)\>*

*\<(Optional) Qualitäts-/Leistungsmerkmale\>*

*\<(Optional) Ablageort/Datei(en)\>*

*\<(Optional) Erfüllte Anforderungen\>*

*\<(optional) Offene Punkte/Probleme/Risiken\>*

### \<Name Blackbox 2\> {#__name_blackbox_2}

*\<Blackbox-Template\>*

### \<Name Blackbox n\> {#__name_blackbox_n}

*\<Blackbox-Template\>*

### \<Name Schnittstelle 1\> {#__name_schnittstelle_1}

...

### \<Name Schnittstelle m\> {#__name_schnittstelle_m}

## Ebene 2 {#_ebene_2}

### Whitebox *\<Baustein 1\>* {#_whitebox_emphasis_baustein_1_emphasis}

*\<Whitebox-Template\>*

### Whitebox *\<Baustein 2\>* {#_whitebox_emphasis_baustein_2_emphasis}

*\<Whitebox-Template\>*

...

### Whitebox *\<Baustein m\>* {#_whitebox_emphasis_baustein_m_emphasis}

*\<Whitebox-Template\>*

## Ebene 3 {#_ebene_3}

### Whitebox \<\_Baustein x.1\_\> {#_whitebox_baustein_x_1}

*\<Whitebox-Template\>*

### Whitebox \<\_Baustein x.2\_\> {#_whitebox_baustein_x_2}

*\<Whitebox-Template\>*

### Whitebox \<\_Baustein y.1\_\> {#_whitebox_baustein_y_1}

*\<Whitebox-Template\>*

# Laufzeitsicht {#section-runtime-view}

## *\<Bezeichnung Laufzeitszenario 1\>* {#__emphasis_bezeichnung_laufzeitszenario_1_emphasis}

-   \<hier Laufzeitdiagramm oder Ablaufbeschreibung einfügen\>

-   \<hier Besonderheiten bei dem Zusammenspiel der Bausteine in diesem
    Szenario erläutern\>

## *\<Bezeichnung Laufzeitszenario 2\>* {#__emphasis_bezeichnung_laufzeitszenario_2_emphasis}

...

## *\<Bezeichnung Laufzeitszenario n\>* {#__emphasis_bezeichnung_laufzeitszenario_n_emphasis}

...

# Verteilungssicht {#section-deployment-view}

## Infrastruktur Ebene 1 {#_infrastruktur_ebene_1}

***\<Übersichtsdiagramm\>***

Begründung

:   *\<Erläuternder Text\>*

Qualitäts- und/oder Leistungsmerkmale

:   *\<Erläuternder Text\>*

Zuordnung von Bausteinen zu Infrastruktur

:   *\<Beschreibung der Zuordnung\>*

## Infrastruktur Ebene 2 {#_infrastruktur_ebene_2}

### *\<Infrastrukturelement 1\>* {#__emphasis_infrastrukturelement_1_emphasis}

*\<Diagramm + Erläuterungen\>*

### *\<Infrastrukturelement 2\>* {#__emphasis_infrastrukturelement_2_emphasis}

*\<Diagramm + Erläuterungen\>*

...

### *\<Infrastrukturelement n\>* {#__emphasis_infrastrukturelement_n_emphasis}

*\<Diagramm + Erläuterungen\>*

# Querschnittliche Konzepte {#section-concepts}

## *\<Konzept 1\>* {#__emphasis_konzept_1_emphasis}

*\<Erklärung\>*

## *\<Konzept 2\>* {#__emphasis_konzept_2_emphasis}

*\<Erklärung\>*

...

## *\<Konzept n\>* {#__emphasis_konzept_n_emphasis}

*\<Erklärung\>*

# Architekturentscheidungen {#section-design-decisions}

# Qualitätsanforderungen {#section-quality-scenarios}

::: formalpara-title
**Weiterführende Informationen**
:::

Siehe [Qualitätsanforderungen](https://docs.arc42.org/section-10/) in
der online-Dokumentation (auf Englisch!).

## Qualitätsbaum {#_qualit_tsbaum}

## Qualitätsszenarien {#_qualit_tsszenarien}

# Risiken und technische Schulden {#section-technical-risks}

# Glossar {#section-glossary}

+-----------------------+-----------------------------------------------+
| Begriff               | Definition                                    |
+=======================+===============================================+
| *\<Begriff-1\>*       | *\<Definition-1\>*                            |
+-----------------------+-----------------------------------------------+
| *\<Begriff-2*         | *\<Definition-2\>*                            |
+-----------------------+-----------------------------------------------+
