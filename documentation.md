
## 27.09.23

UMLEdit ist nicht umsetzbar da der Arbeitsaufwand zu groß ist, wenn wir eine eigene Sprache hinzufügen wollen und das Projekt ohne diese keinen Sinn macht.  
  
### Neue Idee

Ein Plugin zum Konvertieren zwischen Datenspeicherformaten (xml, json, usw.).

## 03.10.23

Neues Git-Hub weil wie den Projektnamen sonst nicht ändern können. Nehmen jetzt einen generischen Namen.

---

### Geplante Struktur

- Daten in einem Zwischencode speichen (z.B. xml → Zwischencode → json)
- Den Zwischencode als Composite-Pattern
- Erstellen der Dateien mit dem "Builder-Pattern"

---

Wir haben uns mir den `Actions` und `ActionGroups` auseinandergesetzt und konnten ein Mockup erstellen.

> Das ist der Teil des Projekts, der unsern Code in die IDE einbindet.

Wie haben unsere `Group` und `Actions` Kontext-abhängig gemacht.

> - `Group` wird nur angezeigt, wenn der Dateityp unterstützt wird.
> - `Actions` werden nicht für den eigenen Dateityp angezeigt. (kein xml zu xml)

Da wir nicht wussten, wie IntelliJ die Datentypen nennt, haben wir uns dafür eine kleine extra `Action` gebastelt

Für den `ActionUpdateThread` sollten wir uns eine Option aussuchen. Wir haben uns für `BGT` entschieden da das ohne weiteres funktioniert. (Kann man sich nochmal angucken)

Wir haben für `GetFiletypeName` erstmal ein `Confirmation`-Fenster verwendet. Wir müssen uns später für unsere anderen Fenster sowieso nochmal Swing angucken.

In den `Actions` mussten wir die Typen nach den wir filtern leider hardcoden, da `Actions`, die von `AnAction` erben, keine Klassenfelder haben dürfen. ([siehe IntelliJDocs](https://plugins.jetbrains.com/docs/intellij/basic-action-system.html#action-implementation))

## 04.10.23

Das vorläufige Options-Fenster wurde durch ein simples Swing-Fester über `ComponentPopupBuilder` ausgetauscht.
Dieses wird noch um Funktionen erweitert aber voraussichtlich nicht in der Implementierung verändert.

In der `actionPerformed` Funktion haben wir die folgende Codezeile:

```kotlin
val popupPanel = OptionsPanel(EFileType.XML, EFileType.valueOf(e.getData(CommonDataKeys.PSI_FILE)?.fileType?.name!!))
```

Es gibt zwei potenzielle Fehlerquellen in der Zeile.

1. Wir haben zwei stellen an dem Variablen null sein könnten von denen wir behaupten, dass sie es nicht sind.
2. Da wir einen String zu einem Enum umwandeln kann dort ein Fehler auftreten, falls dem String kein Wert zugewiesen werden kann.

Wir haben die Menüs so gestaltet, dass besagte Variable nicht null oder außerhalb der erwarteten Parameter liegen können.
