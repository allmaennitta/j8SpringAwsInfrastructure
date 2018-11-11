package de.allmaennitta.dummy.javaws.shared.utils;

import java.util.ArrayList;
import java.util.List;

public class IataCodesHelper {

  private static IataCodesHelper instance = null;

  private List<IataCode> codes = new ArrayList<IataCode>();

  public static IataCodesHelper getInstance() {
    if (instance == null) {
      instance = new IataCodesHelper();
    }
    return instance;
  }

  public String findByIataCode(String iataCode) {
    for (IataCode code : codes) {
      if (code.getIATACode().equals(iataCode)) {
        return code.getManufacturerAircraftTypeModel();
      }
    }
    return iataCode;
  }

  public IataCodesHelper() {
    codes.add(new IataCode("100", "F100", "Fokker 100", "M"));
    codes.add(new IataCode("141", "B461", "BAe 146-100", "M"));
    codes.add(new IataCode("142", "B462", "BAe 146-200", "M"));
    codes.add(new IataCode("143", "B463", "BAe 146-300", "M"));
    codes.add(new IataCode("146", "", "BAe 146", "M"));
    codes.add(new IataCode("14F", "", "BAe 146 Freighter (-100/200/300QT & QC)", "M"));
    codes.add(new IataCode("14X", "B461", "BAe 146 Freighter (-100QT & QC)", "M"));
    codes.add(new IataCode("14Y", "B462", "BAe 146 Freighter (-200QT & QC)", "M"));
    codes.add(new IataCode("14Z", "B463", "BAe 146 Freighter (-200QT & QC)", "M"));
    codes.add(new IataCode("310", "A310", "Airbus A310", "H"));
    codes.add(new IataCode("312", "A310", "Airbus A310-200", "H"));
    codes.add(new IataCode("313", "A310", "Airbus A310-300", "H"));
    codes.add(new IataCode("318", "A318", "Airbus A318", "M"));
    codes.add(new IataCode("319", "A319", "Airbus A319", "M"));
    codes.add(new IataCode("31F", "A310", "Airbus A310 Freighter", "M"));
    codes.add(new IataCode("31X", "A310", "Airbus A310-200 Freighter", "M"));
    codes.add(new IataCode("31Y", "A310", "Airbus A310-300 Freighter", "M"));
    codes.add(new IataCode("320", "A320", "Airbus A320-100/200", "M"));
    codes.add(new IataCode("321", "A321", "Airbus A321-100/200", "M"));
    codes.add(new IataCode("32S", "n/a", "Airbus A318/319/320/321", "M"));
    codes.add(new IataCode("330", "A330", "Airbus A330 all models", "H"));
    codes.add(new IataCode("332", "A332", "Airbus A330-200", "H"));
    codes.add(new IataCode("333", "A333", "Airbus A330-300", "H"));
    codes.add(new IataCode("33F", "A332", "Airbus A330 Freighter", "H"));
    codes.add(new IataCode("33X", "A332", "Airbus A330-200 Freighter", "H"));
    codes.add(new IataCode("340", "A340", "Airbus A340 all models", "H"));
    codes.add(new IataCode("342", "A342", "Airbus A340-200", "H"));
    codes.add(new IataCode("343", "A343", "Airbus A340-300", "H"));
    codes.add(new IataCode("345", "A345", "Airbus A340-500", "H"));
    codes.add(new IataCode("346", "A346", "Airbus A340-600", "H"));
    codes.add(new IataCode("351", "A35K", "Airbus A350-1000", "H"));
    codes.add(new IataCode("359", "A359", "Airbus A350-900", "H"));
    codes.add(new IataCode("380", "A388", "Airbus A380", "H"));
    codes.add(new IataCode("38F", "", "Airbus A380 Freighter", "H"));
    codes.add(new IataCode("703", "B703", "Boeing 707-300", "H"));
    codes.add(new IataCode("707", "n/a", "Boeing 707/720", "H"));
    codes.add(new IataCode("70F", "B703", "Boeing 707 Freighter", "H"));
    codes.add(new IataCode("70M", "B703", "Boeing 707 Combi", "H"));
    codes.add(new IataCode("717", "B712", "Boeing 717", "M"));
    codes.add(new IataCode("721", "B721", "Boeing 727-100", "M"));
    codes.add(new IataCode("722", "B722", "Boeing 727-200", "M"));
    codes.add(new IataCode("727", "n/a", "Boeing 727", "M"));
    codes.add(new IataCode("72B", "B721", "Boeing 727-100 Mixed Configuration", "M"));
    codes.add(new IataCode("72C", "B722", "Boeing 727-200 Mixed Configuration", "M"));
    codes.add(new IataCode("72F", "n/a", "Boeing 727 Freighter (-100/200)", "M"));
    codes.add(new IataCode("72M", "n/a", "Boeing 727 Combi", "M"));
    codes.add(new IataCode("72S", "B722", "Boeing 727-200 Advanced", "M"));
    codes.add(new IataCode("72W", "B721", "Boeing 727-200 (winglets)", "M"));
    codes.add(new IataCode("72X", "B721", "Boeing 727-100 Freighter", "M"));
    codes.add(new IataCode("72Y", "B722", "Boeing 727-200 Freighter", "M"));
    codes.add(new IataCode("731", "B731", "Boeing 737-100", "M"));
    codes.add(new IataCode("732", "B732", "Boeing 737-200", "M"));
    codes.add(new IataCode("733", "B733", "Boeing 737-300", "M"));
    codes.add(new IataCode("734", "B734", "Boeing 737-400", "M"));
    codes.add(new IataCode("735", "B735", "Boeing 737-500", "M"));
    codes.add(new IataCode("736", "B736", "Boeing 737-600", "M"));
    codes.add(new IataCode("737", "n/a", "Boeing 737", "M"));
    codes.add(new IataCode("738", "B738", "Boeing 737-800", "M"));
    codes.add(new IataCode("739", "B739", "Boeing 737-900", "M"));
    codes.add(new IataCode("73C", "B733", "Boeing 737-300 (winglets)", "M"));
    codes.add(new IataCode("73E", "B735", "Boeing 737-500 (winglets)pax", "M"));
    codes.add(new IataCode("73F", "n/a", "Boeing 737 all Freighter models", "M"));
    codes.add(new IataCode("73G", "B737", "Boeing 737-700", "M"));
    codes.add(new IataCode("73H", "B738", "Boeing 737-800 (winglets)", "M"));
    codes.add(new IataCode("73J", "B739", "Boeing 737-900 (winglets)", "M"));
    codes.add(new IataCode("73L", "B732", "Boeing 737-200 Combi", "M"));
    codes.add(new IataCode("73M", "n/a", "Boeing 737 Combi", "M"));
    codes.add(new IataCode("73P", "B734", "Boeing 737-400 Freighter", "M"));
    codes.add(new IataCode("73Q", "B734", "Boeing 737-400 Combi", "M"));
    codes.add(new IataCode("73R", "B737", "Boeing 737-700 Combi", "M"));
    codes.add(new IataCode("73W", "B737", "Boeing 737-700 (winglets)", "M"));
    codes.add(new IataCode("73X", "B732", "Boeing 737-200 Freighter", "M"));
    codes.add(new IataCode("73Y", "B733", "Boeing 737-300 Freighter", "M"));
    codes.add(new IataCode("741", "B741", "Boeing 747-100", "H"));
    codes.add(new IataCode("742", "B742", "Boeing 747-200", "H"));
    codes.add(new IataCode("743", "B743", "Boeing 747-300", "H"));
    codes.add(new IataCode("744", "B744", "Boeing 747-400", "H"));
    codes.add(new IataCode("747", "n/a", "Boeing 747", "H"));
    codes.add(new IataCode("748", "B748", "Boeing 747-8", "H"));
    codes.add(new IataCode("74B", "B744", "Boeing 747-400 Swingtail Freighter", "H"));
    codes.add(new IataCode("74C", "B742", "Boeing 747-200 Combi", "H"));
    codes.add(new IataCode("74D", "B743", "Boeing 747-300 Combi", "H"));
    codes.add(new IataCode("74E", "B744", "Boeing 747-400 Combi", "H"));
    codes.add(new IataCode("74F", "n/a", "Boeing 747 all Freighter models", "H"));
    codes.add(new IataCode("74J", "B744", "Boeing 747-400 (Domestic)", "H"));
    codes.add(new IataCode("74L", "N74S", "Boeing 747SP", "H"));
    codes.add(new IataCode("74M", "n/a", "Boeing 747 all Combi models", "H"));
    codes.add(new IataCode("74R", "B74R", "Boeing 747SR", "H"));
    codes.add(new IataCode("74T", "B741", "Boeing 747-100 Freighter", "H"));
    codes.add(new IataCode("74U", "B743", "Boeing 747-300 / 747-200 SUD Freighter", "H"));
    codes.add(new IataCode("74V", "B74R", "Boeing 747SR Freighter", "H"));
    codes.add(new IataCode("74X", "B742", "Boeing 747-200 Freighter", "H"));
    codes.add(new IataCode("74Y", "B744", "Boeing 747-400 Freighter", "H"));
    codes.add(new IataCode("752", "B752", "Boeing 757-200", "H"));
    codes.add(new IataCode("753", "B753", "Boeing 757-300", "H"));
    codes.add(new IataCode("757", "n/a", "Boeing 757", "H"));
    codes.add(new IataCode("75F", "B752", "Boeing 757 Freighter", "H"));
    codes.add(new IataCode("75M", "B752", "Boeing 757 Mixed Configuration", "H"));
    codes.add(new IataCode("75W", "B752", "Boeing 757-200 (winglets)", "H"));
    codes.add(new IataCode("762", "B762", "Boeing 767-200", "H"));
    codes.add(new IataCode("763", "B763", "Boeing 767-300", "H"));
    codes.add(new IataCode("764", "B764", "Boeing 767-400", "H"));
    codes.add(new IataCode("767", "n/a", "Boeing 767", "H"));
    codes.add(new IataCode("76F", "n/a", "Boeing 767 all Freighter models", "H"));
    codes.add(new IataCode("76W", "B763", "Boeing 767-300 (winglets)", "H"));
    codes.add(new IataCode("76X", "B762", "Boeing 767-200 Freighter", "H"));
    codes.add(new IataCode("76Y", "B763", "Boeing 767-300 Freighter", "H"));
    codes.add(new IataCode("772", "B772", "Boeing 777-200", "H"));
    codes.add(new IataCode("773", "B773", "Boeing 777-300", "H"));
    codes.add(new IataCode("777", "n/a", "Boeing 777", "H"));
    codes.add(new IataCode("77F", "n/a", "Boeing 777 Freighter", "H"));
    codes.add(new IataCode("77L", "B772", "Boeing 777-200LR", "H"));
    codes.add(new IataCode("77W", "B77W", "Boeing 777-300ER", "H"));
    codes.add(new IataCode("77X", "B77L", "Boeing 777-200 Freighter", "H"));
    codes.add(new IataCode("783", "B783", "Boeing 787-3", "H"));
    codes.add(new IataCode("788", "B788", "Boeing 787-8", "H"));
    codes.add(new IataCode("789", "B789", "Boeing 787-9", "H"));
    codes.add(new IataCode("7M7", "B37M", "Boeing 737 MAX 7", "M"));
    codes.add(new IataCode("7M8", "B38M", "Boeing 737 MAX 8", "M"));
    codes.add(new IataCode("7M9", "B39M", "Boeing 737 MAX 9", "M"));
    codes.add(new IataCode("A22", "AN22", "Antonov AN-22", "L"));
    codes.add(new IataCode("A26", "AN26", "Antonov AN-26", "L"));
    codes.add(new IataCode("A28", "AN28", "Antonov AN-28 / PZL Miele M-28 Skytruck", "L"));
    codes.add(new IataCode("A30", "AN30", "Antonov AN-30", "L"));
    codes.add(new IataCode("A32", "AN32", "Antonov AN-32", "L"));
    codes.add(new IataCode("A38", "AN38", "Antonov AN-38", "L"));
    codes.add(new IataCode("A40", "A140", "Antonov AN-140", "M"));
    codes.add(new IataCode("A4F", "A124", "Antonov AN-124 Ruslan", "H"));
    codes.add(new IataCode("A5F", "A225", "Antonov AN-225", "H"));
    codes.add(new IataCode("A81", "A148", "Antonov AN-148-100", "M"));
    codes.add(new IataCode("AB3", "A30B", "Airbus Industrie A300", "H"));
    codes.add(new IataCode("AB4", "A30B", "Airbus Industrie A300B2/B4/C4", "H"));
    codes.add(new IataCode("AB6", "A306", "Airbus Industrie A300-600", "H"));
    codes.add(new IataCode("ABB", "A3ST", "Airbus Industrie A300-600ST Beluga Freighter", "H"));
    codes.add(new IataCode("ABF", "A30B", "Airbus Industrie A300 Freighter", "H"));
    codes.add(new IataCode("ABX", "A30B", "Airbus Industrie A300C4/F4 Freighter", "H"));
    codes.add(new IataCode("ABY", "A306", "Airbus Industrie A600-600 Freighter", "H"));
    codes.add(new IataCode("ACD", "n/a", "Gulfstream/Rockwell (Aero) Commander/Turbo Commander", "L"));
    codes.add(new IataCode("ACP", "AC68", "Gulfstream/Rockwell (Aero) Commander", "L"));
    codes.add(new IataCode("ACT", "AC90", "Gulfstream/Rockwell (Aero) Turbo Commander", "L"));
    codes.add(new IataCode("AGH", "A109", "Agusta A109", "n/a"));
    codes.add(new IataCode("ALM", "LOAD", "Ayres LM-200 Loadmaster", "M"));
    codes.add(new IataCode("AN4", "AN24", "Antonov AN-24", "M"));
    codes.add(new IataCode("AN6", "n/a", "Antonov AN-26 / AN-30 /AN-32", "M"));
    codes.add(new IataCode("AN7", "AN72", "Antonov AN-72 / AN-74", "M"));
    codes.add(new IataCode("ANF", "AN12", "Antonov AN-12", "M"));
    codes.add(new IataCode("APH", "n/a", "Eurocopter (Aerospatiale) SA330 Puma / AS332 Super Puma", "n/a"));
    codes.add(new IataCode("AR1", "RJ1H", "Avro RJ100 Avroliner", "M"));
    codes.add(new IataCode("AR7", "RJ70", "Avro RJ70 Avroliner", "M"));
    codes.add(new IataCode("AR8", "RJ85", "Avro RJ85 Avroliner", "M"));
    codes.add(new IataCode("ARJ", "n/a", "Avro RJ70 / RJ85 / RJ100 Avroliner", "M"));
    codes.add(new IataCode("ARX", "n/a", "Avro RJX85 / RJX100", "M"));
    codes.add(new IataCode("AT4", "AT43", "Aerospatiale/Alenia ATR 42-300 / 320", "M"));
    codes.add(new IataCode("AT5", "AT45", "Aerospatiale/Alenia ATR 42-500", "M"));
    codes.add(new IataCode("AT7", "AT72", "Aerospatiale/Alenia ATR 72", "M"));
    codes.add(new IataCode("ATD", "AT44", "Aerospatiale/Alenia ATR 42-400", "M"));
    codes.add(new IataCode("ATF", "AT72", "Aerospatiale/Alenia ATR 72 Freighter", "M"));
    codes.add(new IataCode("ATP", "ATP", "British Aerospace ATP", "M"));
    codes.add(new IataCode("ATR", "n/a", "Aerospatiale/Alenia ATR 42/ ATR 72", "M"));
    codes.add(new IataCode("AX1", "RX1H", "Avro RJX100", "M"));
    codes.add(new IataCode("AX8", "RX85", "Avro RJX85", "M"));
    codes.add(new IataCode("B11", "BA11", "British Aerospace (BAC) One Eleven / RomBAC One Eleven", "M"));
    codes.add(new IataCode("B12", "BA11", "British Aerospace (BAC) One Eleven 200", "M"));
    codes.add(new IataCode("B13", "BA11", "British Aerospace (BAC) One Eleven 300", "M"));
    codes.add(new IataCode("B14", "BA11", "British Aerospace (BAC) One Eleven 400/475", "M"));
    codes.add(new IataCode("B15", "BA11", "British Aerospace (BAC) One Eleven 500 / RomBAC One Eleven", "M"));
    codes.add(new IataCode("B72", "B720", "Boeing 720B", "M"));
    codes.add(new IataCode("BE1", "B190", "Beechcraft 1900/1900C/1900D", "M"));
    codes.add(new IataCode("BE2", "n/a", "Beechcraft twin piston engines", "L"));
    codes.add(new IataCode("BEC", "n/a", "Beechcraft light aircraft", "L"));
    codes.add(new IataCode("BEH", "B190", "Beechcraft 1900D", "M"));
    codes.add(new IataCode("BEP", "n/a", "Beechcraft light aircraft - single engine", "L"));
    codes.add(new IataCode("BES", "B190", "Beechcfrat 1900/1900C", "M"));
    codes.add(new IataCode("BET", "n/a", "Beechcraft light aircraft - twin turboprop engine", "L"));
    codes.add(new IataCode("BH2", "n/a", "Bell Helicopters", "n/a"));
    codes.add(new IataCode("BNI", "BN2P", "Pilatus Britten-Norman BN-2A/B Islander", "L"));
    codes.add(new IataCode("BNT", "TRIS", "Pilatus Britten-Norman BN-2A Mk III Trislander", "L"));
    codes.add(new IataCode("BUS", "n/a", "Bus", "n/a"));
    codes.add(new IataCode("CCJ", "CL60", "Canadair Challenger", "M"));
    codes.add(new IataCode("CCX", "GLEX", "Canadair Global Express", "M"));
    codes.add(new IataCode("CD2", "NOMA", "Government Aircraft Factories N22B / N24A Nomad", "L"));
    codes.add(new IataCode("CL4", "CL44", "Canadair CL-44", "M"));
    codes.add(new IataCode("CN1", "n/a", "Cessna light aircraft - single piston engine", "L"));
    codes.add(new IataCode("CN2", "n/a", "Cessna light aircraft - twin piston engines", "L"));
    codes.add(new IataCode("CN7", "C750", "Cessna 750 Citation X", "M"));
    codes.add(new IataCode("CNA", "n/a", "Cessna light aircraft", "L"));
    codes.add(new IataCode("CNC", "n/a", "Cessna light aircraft - single turboprop engine", "L"));
    codes.add(new IataCode("CNJ", "n/a", "Cessna Citation", "L"));
    codes.add(new IataCode("CNT", "n/a", "Cessna light aircraft - twin turboprop engines", "L"));
    codes.add(new IataCode("CR1", "CRJ1", "Canadair Regional Jet 100", "M"));
    codes.add(new IataCode("CR2", "CRJ2", "Canadair Regional Jet 200", "M"));
    codes.add(new IataCode("CR7", "CRJ7", "Canadair Regional Jet 700", "M"));
    codes.add(new IataCode("CR9", "CRJ9", "Canadair Regional Jet 900", "M"));
    codes.add(new IataCode("CRA", "CRJ9", "Canadair Regional Jet 705", "M"));
    codes.add(new IataCode("CRF", "n/a", "Canadair Regional Jet Freighter", "M"));
    codes.add(new IataCode("CRJ", "n/a", "Canadair Regional Jet", "M"));
    codes.add(new IataCode("CRK", "CRJX", "Canadair Regional Jet 1000", "M"));
    codes.add(new IataCode("CRV", "S210", "Aerospatiale (Sud Aviation) Se.210 Caravelle", "M"));
    codes.add(new IataCode("CS2", "C212", "CASA / IPTN 212 Aviocar", "M"));
    codes.add(new IataCode("CS5", "CN35", "CASA / IPTN CN-235", "M"));
    codes.add(new IataCode("CV2", "CVLP", "Convair CV-240", "M"));
    codes.add(new IataCode("CV4", "CVLP", "Convair CV-440 Metropolitan", "M"));
    codes.add(new IataCode("CV5", "CVLT", "Convair CV-580", "M"));
    codes.add(new IataCode("CVF", "n/a", "Convair CV-240 / 440 / 580 / 600 / 640 Freighter", "M"));
    codes.add(new IataCode("CVR", "n/a", "Convair CV-240 / 440 / 580 / 600 / 640", "M"));
    codes.add(new IataCode("CVV", "CVLP", "Convair CV-240 Freighter", "M"));
    codes.add(new IataCode("CVX", "CVLP", "Convair CV-440 Freighter", "M"));
    codes.add(new IataCode("CVY", "CVLT", "Convair CV-580 / 600 / 640 Freighter", "M"));
    codes.add(new IataCode("CWC", "C46", "Curtiss C-46 Commando", "M"));
    codes.add(new IataCode("D10", "DC10", "Douglas DC-10", "H"));
    codes.add(new IataCode("D11", "DC10", "Douglas DC-10-10/15", "H"));
    codes.add(new IataCode("D1C", "DC10", "Douglas DC-10-30/40", "H"));
    codes.add(new IataCode("D1F", "DC10", "Douglas DC-10 all Freighters", "H"));
    codes.add(new IataCode("D1M", "DC10", "Douglas DC-10 all Combi models", "H"));
    codes.add(new IataCode("D1X", "DC10", "Douglas DC-10-10 Freighter", "H"));
    codes.add(new IataCode("D1Y", "DC10", "Douglas DC-10-30 / 40 Freighters", "H"));
    codes.add(new IataCode("D28", "D228", "Fairchild Dornier Do.228", "L"));
    codes.add(new IataCode("D38", "D328", "Fairchild Dornier Do.328", "M"));
    codes.add(new IataCode("D3F", "DC3", "Douglas DC-3 Freighter", "M"));
    codes.add(new IataCode("D6F", "DC6", "Douglas DC-6A/B/C Freighter", "M"));
    codes.add(new IataCode("D8F", "n/a", "Douglas DC-8 all Freighters", "H"));
    codes.add(new IataCode("D8L", "DC86", "Douglas DC-8-62", "H"));
    codes.add(new IataCode("D8M", "n/a", "Douglas DC-8 all Combi models", "H"));
    codes.add(new IataCode("D8Q", "DC87", "Douglas DC-8-72", "H"));
    codes.add(new IataCode("D8T", "DC85", "Douglas DC-8-50 Freighter", "H"));
    codes.add(new IataCode("D8X", "n/a", "Douglas DC-8-61 / 62 / 63 Freighters", "H"));
    codes.add(new IataCode("D8Y", "DC87", "Douglas DC-8-71 / 72 / 73 Freighters", "H"));
    codes.add(new IataCode("D91", "DC91", "Douglas DC-9-10", "M"));
    codes.add(new IataCode("D92", "DC92", "Douglas DC-9-20", "M"));
    codes.add(new IataCode("D93", "DC93", "Douglas DC-9-30", "M"));
    codes.add(new IataCode("D94", "DC94", "Douglas DC-9-40", "M"));
    codes.add(new IataCode("D95", "DC95", "Douglas DC-9-50", "M"));
    codes.add(new IataCode("D9C", "DC93", "Douglas DC-9-30 Freighter", "M"));
    codes.add(new IataCode("D9D", "DC94", "Douglas DC-9-40 Freighter", "M"));
    codes.add(new IataCode("D9F", "n/a", "Douglas DC-9 all Freighters", "M"));
    codes.add(new IataCode("D9X", "DC91", "Douglas DC-9-10 Freighter", "M"));
    codes.add(new IataCode("DC3", "DC3", "Douglas DC-3", "M"));
    codes.add(new IataCode("DC4", "DC4", "Douglas DC-4", "M"));
    codes.add(new IataCode("DC6", "DC6", "Douglas DC6A/B", "M"));
    codes.add(new IataCode("DC8", "n/a", "Douglas DC-8", "H"));
    codes.add(new IataCode("DC9", "DC9", "Douglas DC-9", "M"));
    codes.add(new IataCode("DF2", "n/a", "Dassault (Breguet Mystere) Falcon 10 / 100 / 20 / 200 / 2000", "M"));
    codes.add(new IataCode("DF3", "n/a", "Dassault (Breguet Mystere) Falcon 50 / 900", "M"));
    codes.add(new IataCode("DFL", "n/a", "Dassault (Breguet Mystere) Falcon", "M"));
    codes.add(new IataCode("DH1", "DH8A", "De Havilland Canada DHC-8-100 Dash 8 / 8Q", "M"));
    codes.add(new IataCode("DH2", "DH8B", "De Havilland Canada DHC-8-200 Dash 8 / 8Q", "M"));
    codes.add(new IataCode("DH3", "DH8C", "De Havilland Canada DHC-8-300 Dash 8 / 8Q", "M"));
    codes.add(new IataCode("DH4", "DH8D", "De Havilland Canada DHC-8-400 Dash 8Q", "M"));
    codes.add(new IataCode("DH7", "DHC7", "De Havilland Canada DHC-7 Dash 7", "M"));
    codes.add(new IataCode("DH8", "n/a", "De Havilland Canada DHC-8 Dash 8 all models", "M"));
    codes.add(new IataCode("DHB", "n/a", "De Havilland Canada DHC-2 Beaver / Turbo Beaver", "L"));
    codes.add(new IataCode("DHC", "DHC4", "De Havilland Canada DHC-4 Caribou", "M"));
    codes.add(new IataCode("DHD", "DOVE", "De Havilland DH.104 Dove", "L"));
    codes.add(new IataCode("DHH", "HERN", "De Havilland DH.114 Heron", "L"));
    codes.add(new IataCode("DHL", "DHC3", "De Havilland Canada DHC-3 Turbo Otter", "L"));
    codes.add(new IataCode("DHO", "DHC3", "De Havilland Canada DHC-3 Otter / Turbo Otter", "L"));
    codes.add(new IataCode("DHP", "DHC2", "De Havilland Canada DHC-2 Beaver", "L"));
    codes.add(new IataCode("DHR", "DH2T", "De Havilland Canada DHC-2 Turbo-Beaver", "L"));
    codes.add(new IataCode("DHS", "DHC3", "De Havilland Canada DHC-3 Otter", "L"));
    codes.add(new IataCode("DHT", "DHC6", "De Havilland Canada DHC-6 Twin Otter", "L"));
    codes.add(new IataCode("E70", "E170", "Embraer 170", "M"));
    codes.add(new IataCode("E90", "E190", "Embraer 190", "M"));
    codes.add(new IataCode("EC3", "EC30", "Eurocopter EC.130", "n/a"));
    codes.add(new IataCode("E70", "E170", "Embraer 170", "M"));
    codes.add(new IataCode("E75", "E75L", "Embraer 175 (Short wing)", "M"));
    codes.add(new IataCode("E75", "E75S", "Embraer 175 (Long Wing)", "M"));
    codes.add(new IataCode("E90", "E190", "Embraer 190", "M"));
    codes.add(new IataCode("E95", "E195", "Embraer 195", "M"));
    codes.add(new IataCode("EM2", "E120", "Embraer EMB.120 Brasilia", "L"));
    codes.add(new IataCode("EMB", "E110", "Embraer EMB.110 Bandeirnate", "M"));
    codes.add(new IataCode("EMJ", "n/a", "Embraer 170/190", "M"));
    codes.add(new IataCode("ER3", "E135", "Embraer RJ135", "M"));
    codes.add(new IataCode("ER3", "E35L", "Embraer Legacy 600 / Legacy 650", "M"));
    codes.add(new IataCode("ER4", "E145", "Embraer RJ145 Amazon", "M"));
    codes.add(new IataCode("ERD", "n/a", "Embraer RJ140", "M"));
    codes.add(new IataCode("ERJ", "n/a", "Embraer RJ135 / RJ140 / RJ145", "M"));
    codes.add(new IataCode("F21", "F28", "Fokker F.28 Fellowship 1000", "M"));
    codes.add(new IataCode("F22", "F28", "Fokker F.28 Fellowship 2000", "M"));
    codes.add(new IataCode("F23", "F28", "Fokker F.28 Fellowship 3000", "M"));
    codes.add(new IataCode("F24", "F28", "Fokker F.28 Fellowship 4000", "M"));
    codes.add(new IataCode("F27", "F27", "Fokker F.27 Friendship / Fairchild F.27", "M"));
    codes.add(new IataCode("F28", "F28", "Fokker F.28 Fellowship", "M"));
    codes.add(new IataCode("F50", "F50", "Fokker 50", "M"));
    codes.add(new IataCode("F5F", "F50", "Fokker 50 Freighter", "M"));
    codes.add(new IataCode("F70", "F70", "Fokker 70", "M"));
    codes.add(new IataCode("FA7", "n/a", "Fairchild Dornier 728JET", "M"));
    codes.add(new IataCode("FK7", "F27", "Fairchild FH.227", "M"));
    codes.add(new IataCode("FRJ", "J328", "Fairchild Dornier 328JET", "M"));
    codes.add(new IataCode("GRG", "G21", "Grumman G.21 Goose", "L"));
    codes.add(new IataCode("GRJ", "n/a", "Gulfstream Aerospace G-1159 Gulfstream II / III / IV / V", "M"));
    codes.add(new IataCode("GRM", "G73T", "Grumman G.73 Turbo Mallard", "L"));
    codes.add(new IataCode("GRS", "G159", "Gulfstream Aerospace G-159 Gulfstream I", "M"));
    codes.add(new IataCode("H25", "n/a", "British Aerospace (Hawker Siddeley) HS.125", "M"));
    codes.add(new IataCode("HEC", "COUC", "Helio H-250 Courier / H-295 / 385 Super Courier", "L"));
    codes.add(new IataCode("HOV", "n/a", "Hovercraft", "n/a"));
    codes.add(new IataCode("HS7", "A748", "Hawker Siddeley HS.748", "M"));
    codes.add(new IataCode("I14", "I114", "Ilyushin IL114", "M"));
    codes.add(new IataCode("I93", "IL96", "Ilyushin IL96-300", "H"));
    codes.add(new IataCode("I9F", "IL96", "Ilyushin IL96 Freighters", "H"));
    codes.add(new IataCode("I9M", "IL96", "Ilyushin IL96M", "H"));
    codes.add(new IataCode("I9X", "IL96", "Ilyushin IL96-300 Freighter", "H"));
    codes.add(new IataCode("I9Y", "IL96", "Ilyushin IL96T Freighter", "H"));
    codes.add(new IataCode("IL6", "IL62", "Ilyushin IL62", "H"));
    codes.add(new IataCode("IL7", "IL76", "Ilyushin IL76", "H"));
    codes.add(new IataCode("IL8", "IL18", "Ilyushin IL18", "M"));
    codes.add(new IataCode("IL9", "IL96", "Ilyushin IL96", "H"));
    codes.add(new IataCode("ILW", "IL86", "Ilyushin IL86", "H"));
    codes.add(new IataCode("J31", "JS31", "British Aerospace Jetstream 31", "L"));
    codes.add(new IataCode("J32", "JS32", "British Aerospace Jetstream 32", "L"));
    codes.add(new IataCode("J41", "JS41", "British Aerospace Jetstream 41", "M"));
    codes.add(new IataCode("JST", "n/a", "British Aerospace Jetstream 31 / 32 / 41", "L/M"));
    codes.add(new IataCode("JU5", "JU52", "Junkers Ju52/3M", "M"));
    codes.add(new IataCode("L10", "L101", "Lockheed L-1011 Tristar", "H"));
    codes.add(new IataCode("L11", "L101", "Lockheed L-1011 1 / 50 / 100 / 150 / 200 / 250 Tristar", "H"));
    codes.add(new IataCode("L15", "L101", "Lockheed L-1011 500 Tristar", "H"));
    codes.add(new IataCode("L1F", "L101", "Lockheed L-1011 Tristar Freighter", "H"));
    codes.add(new IataCode("L49", "CONI", "Lockheed L-1049 Super Constellation", "M"));
    codes.add(new IataCode("L4T", "L410", "LET 410", "L"));
    codes.add(new IataCode("LCH", "n/a", "Launch - Boat", "n/a"));
    codes.add(new IataCode("LMO", "n/a", "Limousine", "n/a"));
    codes.add(new IataCode("LOE", "L188", "Lockheed L-188 Electra", "M"));
    codes.add(new IataCode("LOF", "L188", "Lockheed L-188 Electra Freighter", "M"));
    codes.add(new IataCode("LOH", "C130", "Lockheed L-182 / 282 / 382 (L-100) Hercules", "M"));
    codes.add(new IataCode("LOM", "L188", "Lockheed L-188 Electra Mixed Configuration", "M"));
    codes.add(new IataCode("LRJ", "n/a", "Gates Learjet", "M"));
    codes.add(new IataCode("M11", "MD11", "McDonnell Douglas MD11", "H"));
    codes.add(new IataCode("M1F", "MD11", "McDonnell Douglas MD11 Freighter", "H"));
    codes.add(new IataCode("M1M", "MD11", "McDonnell Douglas MD11 Mixed Configuration", "H"));
    codes.add(new IataCode("M80", "MD80", "McDonnell Douglas MD80", "M"));
    codes.add(new IataCode("M81", "MD81", "McDonnell Douglas MD81", "M"));
    codes.add(new IataCode("M82", "MD82", "McDonnell Douglas MD82", "M"));
    codes.add(new IataCode("M83", "MD83", "McDonnell Douglas MD83", "M"));
    codes.add(new IataCode("M87", "MD87", "McDonnell Douglas MD87", "M"));
    codes.add(new IataCode("M88", "MD88", "McDonnell Douglas MD88", "M"));
    codes.add(new IataCode("M90", "MD90", "McDonnell Douglas MD90", "M"));
    codes.add(new IataCode("MBH", "B105", "Eurocopter (MBB) Bo.105", "n/a"));
    codes.add(new IataCode("MD9", "EXPL", "MD Helicopters MD900 Explorer", "n/a"));
    codes.add(new IataCode("MIH", "MI8", "MIL Mi-8 / Mi-17 / Mi-171 / Mil-172", "n/a"));
    codes.add(new IataCode("MU2", "MU2", "Mitsubishi Mu-2", "L"));
    codes.add(new IataCode("ND2", "N262", "Aerospatiale (Nord) 262", "M"));
    codes.add(new IataCode("NDC", "S601", "Aerospatiale SN.601 Corvette", "L"));
    codes.add(new IataCode("NDE", "n/a", "Eurocopter (Aerospatiale) AS350 Ecureuil / AS355 Ecureuil 2", "n/a"));
    codes.add(new IataCode("NDH", "S65C", "Eurocopter (Aerospatiale) SA365C / SA365N Dauphin 2", "n/a"));
    codes.add(new IataCode("PA1", "n/a", "Piper light aircraft - single piston engine", "L"));
    codes.add(new IataCode("PA2", "n/a", "Piper light aircraft - twin piston engines", "L"));
    codes.add(new IataCode("PAG", "n/a", "Piper light aircraft", "L"));
    codes.add(new IataCode("PAT", "n/a", "Piper light aircraft - twin turboprop engines", "L"));
    codes.add(new IataCode("PL2", "PC12", "Pilatus PC-12", "L"));
    codes.add(new IataCode("PL6", "PC6T", "Pilatus PC-6 Turbo Porter", "L"));
    codes.add(new IataCode("PN6", "P68", "Partenavia P.68", "L"));
    codes.add(new IataCode("RFS", "n/a", "Road Feeder Service - Cargo Truck", "n/a"));
    codes.add(new IataCode("S20", "SB20", "Saab 2000", "M"));
    codes.add(new IataCode("S58", "S58T", "Sikorsky S-58T", "n/a"));
    codes.add(new IataCode("S61", "S61", "Sikorsky S-61", "n/a"));
    codes.add(new IataCode("S76", "S76", "Sikorsky S-76", "n/a"));
    codes.add(new IataCode("SF3", "SF34", "Saab SF340", "M"));
    codes.add(new IataCode("SFB", "SF34", "Saab SF340B", "M"));
    codes.add(new IataCode("SFF", "SF34", "Saab SF340 Freighter", "M"));
    codes.add(new IataCode("SH3", "SH33", "Shorts SD.330", "M"));
    codes.add(new IataCode("SH6", "SH36", "Shorts SD.360", "M"));
    codes.add(new IataCode("SHB", "BELF", "Shorts SC-5 Belfast", "M"));
    codes.add(new IataCode("SHS", "SC7", "Shorts SC-7 Skyvan", "L"));
    codes.add(new IataCode("SSC", "CONC", "Aerospatiale/BAC Concorde", "H"));
    codes.add(new IataCode("SU1", "", "Sukhoi Superjet 100", "M"));
    codes.add(new IataCode("SU7", "", "Sukhoi Superjet 100-75", "M"));
    codes.add(new IataCode("SU9", "SU95", "Sukhoi Superjet 100-95", "M"));
    codes.add(new IataCode("SWM", "n/a", "Fairchild (Swearingen) SA26 / SA226 / SA227 Metro / Merlin / Expediter", "L"));
    codes.add(new IataCode("T20", "T204", "Tupolev Tu-204 / Tu-214", "M"));
    codes.add(new IataCode("T2F", "T204", "Tupolev Tu-204 Freighter", "M"));
    codes.add(new IataCode("TRN", "n/a", "Train", "n/a"));
    codes.add(new IataCode("TU3", "T134", "Tupolev Tu134", "M"));
    codes.add(new IataCode("TU5", "T154", "Tupolev Tu154", "M"));
    codes.add(new IataCode("VCV", "VISC", "Vickers Viscount", "M"));
    codes.add(new IataCode("WWP", "WW24", "Israel Aircraft Industries 1124 Westwind", "M"));
    codes.add(new IataCode("YK2", "YK42", "Yakovlev Yak 42", "M"));
    codes.add(new IataCode("YK4", "YK40", "Yakovlev Yak 40", "M"));
    codes.add(new IataCode("YN2", "Y12", "Harbin Yunshuji Y12", "M"));
    codes.add(new IataCode("YN7", "AN24", "Xian Yunshuji Y7", "M"));
    codes.add(new IataCode("YS1", "YS11", "NAMC YS-11", "M"));
  }
}