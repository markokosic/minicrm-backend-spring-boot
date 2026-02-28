package com.markokosic.minicrm.modules.remuneration;

import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;

import java.math.BigDecimal;

public non-sealed class WeeklyFixedRateRemunerationCalculator  implements IRemunerationCalculator {

	@Override
	public RemunerationSplit calculateRemuneration(BigDecimal revenue, DriverRemunerationConfig config) {

		//option1:
		// Bezahlung immer an vereinbartem Tag -> kommt in ConfigEntity
		// Umsatz unabhängig von Arbeitszeiten und Arbeitstagen
		// is_wfr_settlement?
//		Flexibilität: Wenn der Fahrer Mittwoch frei macht und erst Donnerstag kommt, "schnappt" sich das System die 700 € einfach am Donnerstag.
//		Realitätstreue: Es bildet exakt deinen Cashflow ab. Am Mittwoch (oder Donnerstag) hast du die 700 € in den Büchern, an den anderen Tagen 0 €.


		//option2:
		//user trägt dailyREvenue ein und kann mit checkbox wählen: Möchtest du an diesem Tag die Wochenmiete vom Fahrer eintragen?
		// wenn er true klickt dann kan ner mitsenden companyShare: und den Betrag den er möchte (ist normal fix hinterlegt in DriverRemunerationConfig{
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

		return new RemunerationSplit(revenue, revenue);
	}
}