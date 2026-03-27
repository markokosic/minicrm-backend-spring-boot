package com.markokosic.minicrm.modules.remuneration;

import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import com.markokosic.minicrm.modules.driver.model.WeeklyFixedRateRemunerationConfig;

import java.math.BigDecimal;

public non-sealed class WeeklyFixedRateRemunerationCalculator  implements IRemunerationCalculator {

	@Override
	public RemunerationSplit calculateRemuneration(BigDecimal revenue, DriverRemunerationConfig config) {
		WeeklyFixedRateRemunerationConfig wc = (WeeklyFixedRateRemunerationConfig) config;

		BigDecimal weeklyFixedRate = wc.getWeeklyFixedRate();

		BigDecimal finalDriverShare = revenue.subtract(weeklyFixedRate);

		return new RemunerationSplit(weeklyFixedRate, finalDriverShare);
	}
}


//es gibt settlementDay WEDNESDAY z.B.
// frontend prüft und setzt dann z.B. Wochenmiete vom Fahrer erhalten? true und wenn das der fall ist steht daneben ein Feld mit der Wochenmiete welches anpassbar ist, so habe ich flexibilität öèter einzutragen und aber auch die sicherheit dass das backend halb mitentscheidet was die weekly rate ist weil es im driver renum config gespeichert ist und mitgesendet wird

//✦ Du hast völlig recht: In beiden Fällen muss das Frontend entscheiden, was es sendet. Wenn wir es "simplifizieren" und einfach ein Feld im DTO haben, ist das absolut legitim.
//
//Meine Meinung zur "Sauberkeit":
//Ein Feld wie settlementAmount (oder fixedRateSettlement) im DTO ist die einfachste und flexibelste Lösung. Ob das Frontend dafür eine Checkbox anzeigt oder das Feld immer einblendet, ist eine reine UI-Entscheidung.
//
//		Warum settlementAmount (optional) trotzdem "sauberer" ist als immer 0 zu senden:
//		1. Semantik: null bedeutet "Heute findet keine Abrechnung statt". 0 bedeutet "Ich rechne heute ab, aber der Betrag ist 0". Für deine Statistik später ist es wertvoll zu wissen: "Wann wurde die Wochenmiete
//tatsächlich verbucht?".
//		2. Fehlerschutz: Wenn das Feld optional ist, kann das Backend (der Calculator) eine klare Regel haben: „Nur wenn ein Betrag mitkommt, ziehe ich etwas ab.“ Das verhindert, dass aus Versehen 0€ abgezogen werden,
//obwohl man gar nicht abrechnen wollte.
//
//Mein Vorschlag für die finale, einfache Umsetzung:
//
//		1. DTO:
//		1     public record CreateDailyRevenueRequestDTO(
//   2         // ...
//		   3         BigDecimal revenue,
//   4         BigDecimal settlementAmount // Das optionale Feld für die Miete/Fixrate
//   5     ) {}
//
//   2. Calculator:
//Der WeeklyFixedRateRemunerationCalculator nimmt diesen settlementAmount.
//       * Wenn settlementAmount != null -> companyShare = settlementAmount, driverShare = revenue - settlementAmount.
//       * Wenn null -> companyShare = 0, driverShare = revenue.
//
//Damit hast du alle Fälle abgedeckt:
//		* Normaler Tag: Feld ist leer/null.
//		* Abrechnungstag: Feld ist 600.
//		* Kulanz: Feld wird manuell auf 400 geändert.
//
//Das ist "saubere Simplizität". Du brauchst keine komplexen Flags, sondern nutzt einfach ein Feld, das genau das tut, was es soll.
//
//Soll ich das DTO und die Calculator-Logik jetzt so mit dem Namen settlementAmount umsetzen?
//
//		(P.S.: Die settlementDay Erweiterung in der Config ist trotzdem super, damit das Frontend weiß, wann es dem User den Betrag von 600€ überhaupt erst vorschlagen soll!)
//		> was meinst du daszu?
//
//// frontend prüft und setzt dann z.B. Wochenmiete vom Fahrer erhalten? true und wenn das der fall ist steht daneben ein Feld mit der Wochenmiete welches anpassbar ist, so habe ich flexibilität öèter einzutragen
//und aber auch die sicherheit dass das backend halb mitentscheidet was die weekly rate ist weil es im driver renum config gespeichert ist und mitgesendet wird
//		✦ Das ist die perfekte Kombination aus Sicherheit (Backend-Wissen) und Flexibilität (User-Eingriff).
//
//Hier ist meine fachliche Meinung dazu:
//
//		1. Sicherheit durch "Backend-Pre-fill": Da das Backend die weeklyFixedRate (600 €) in der Config hat, kann das Frontend dieses Feld beim Anhaken der Checkbox sofort korrekt vorausfüllen. Der User muss nicht raten
//oder im Kopf haben, was vereinbart war.
//		2. Flexibilität durch "Manual Override": Das Feld bleibt editierbar. Wenn der Fahrer nur 400 € zahlt, ändert der User es einfach. Das deckt deinen "Kulanz-Usecase" perfekt ab.
//		3. Saubere Daten: Das Backend erhält nur einen Betrag (z.B. 400 €). Es muss nicht wissen, ob das "eigentlich" 600 € sein sollten – es verbucht einfach die 400 € als companyRemuneration.
//
//  ---
//
//So setzen wir das jetzt technisch um:
//
//Ich werde nun die Änderungen durchführen, um genau diesen Workflow zu unterstützen.
//
//		Schritt 1: DTO anpassen (CreateDailyRevenueRequestDTO)
//Wir fügen settlementAmount hinzu. (Wenn null -> keine Abrechnung, wenn Value -> Abrechnung mit diesem Betrag).
//
//Schritt 2: Interface & Service anpassen
//Wir müssen den settlementAmount vom DTO bis zum Calculator durchreichen.
//
//Schritt 3: Calculator Logik implementieren
//Der WeeklyFixedRateRemunerationCalculator wird so angepasst:
//		- Wenn settlementAmount != null: company = settlementAmount, driver = revenue - settlementAmount.
//   - Wenn null: company = 0, driver = revenue.
//
//Ich fange mit dem DTO an.
//




//option2:
//user trägt dailyREvenue ein und kann mit checkbox wählen: Möchtest du an diesem Tag die Wochenmiete vom Fahrer eintragen?
// wenn er true klickt dann kan ner mitsenden companyRemuneration: und den Betrag den er möchte (ist normal fix hinterlegt in DriverRemunerationConfig{
// wochentag ist auch hinterlegt
//frontend prüft ob wochentag heute ist und dann wird autmoatisch gesetzt und ausgefüllt


//		Wenn der User das Formular für dailyRevenue öffnet:
//
//		Das Frontend prüft: driver.remuneration_type === 'WFR'.
//
//				Das Frontend prüft: currentDate.getDayOfWeek() === config.settlementDay (z.B. Mittwoch).
//
//				Aktion: Die Checkbox "Wochenmiete verrechnen" wird automatisch angehakt und das Feld daneben mit den 700 € vorausgefüllt.
//
//backend muss dafür DriverRemuneartionConfig mitsenden im DriverDTO

//		Im Taxi-Business (und in deiner App) passiert folgendes:
//
//		Der Fahrer kommt rein.
//
//		Der Kunde (dein User/Chef) fragt: "Hast du die Miete für diese Woche dabei?"
//
//		Der Fahrer sagt: "Ja, hier sind die 600 €."
//
//		Der Kunde trägt den Umsatz ein, setzt den Haken (oder gibt den Betrag ein) und dokumentiert damit den Cashflow.
//