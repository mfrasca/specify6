package se.nrm.specify.smtp.util;

import java.util.ArrayList;
import java.util.List;
import se.nrm.specify.smtp.beans.SampleBean;

/**
 *
 * @author idali
 */
public class Constant {
  
    public static final String SORTING_GROUP_NAME_RAW_SAMPLE = "Raw Trap Sample";
    public static final String SORTING_GROUP_NAME_BRACHCERA = "Brachycera";
    public static final String SORTING_GROUP_NAME_NEMATOCERA = "Nematocera";
    public static final String SORTING_GROUP_NAME_HYMENOPTERA = "Hymenoptera";
    public static final String SORTING_GROUP_NAME_BRACONIDAE = "Braconidae";
    public static final String SORTING_GROUP_NAME_ICHNEUMONIDAE = "Ichneumonidae";
    public static final String SORTING_GROUP_NAME_CHALCIDOIDEA = "Chalcidoidea";
    
    public static final String RAW_SAMPLE_TAXON = "Arthropoda"; 
    
    public static final String SMTP_RAW_SAMPLE = "SMTP Raw Sample";
    public static final String SMTP_TRAP = "SMTP Trap";
    public static final String SMTP_TAXON = "SMTP ";
    public static final String SMTP_PROGRESS = "SMTP Progress";
         
    public static List<SampleBean> getRawTrapSampleList() {
        List<SampleBean> rawTrapSampleList = new ArrayList<SampleBean>();
        
        rawTrapSampleList.add(new SampleBean("Acari", "", "Acari"));
        rawTrapSampleList.add(new SampleBean("Araneae", "", "Araneae"));
        rawTrapSampleList.add(new SampleBean("Asilidae", "", "Asilidae"));
        rawTrapSampleList.add(new SampleBean("Auchenorrhyncha", "", "Auchenorrhyncha"));
        rawTrapSampleList.add(new SampleBean("Blattodea", "", "Blattodea"));
        rawTrapSampleList.add(new SampleBean("Brachycera", "", "Brachycera"));
        rawTrapSampleList.add(new SampleBean("Coleoptera", "", "Coleoptera"));
        rawTrapSampleList.add(new SampleBean("Collembola", "", "Collembola"));
        rawTrapSampleList.add(new SampleBean("Dermaptera", "", "Dermaptera"));
        rawTrapSampleList.add(new SampleBean("Ephemeroptera", "", "Ephemeroptera"));
        rawTrapSampleList.add(new SampleBean("Heteroptera", "", "Heteroptera"));
        rawTrapSampleList.add(new SampleBean("Hymenoptera", "", "Hymenoptera"));
        rawTrapSampleList.add(new SampleBean("Isopoda", "", "Isopoda"));
        rawTrapSampleList.add(new SampleBean("Mecoptera", "", "Mecoptera"));
        rawTrapSampleList.add(new SampleBean("Megaloptera", "", "Megaloptera"));
        rawTrapSampleList.add(new SampleBean("Myriapoda", "", "Myriapoda"));
        rawTrapSampleList.add(new SampleBean("Nematocera", "", "Nematocera"));
        rawTrapSampleList.add(new SampleBean("Neuroptera", "", "Neuroptera"));
        rawTrapSampleList.add(new SampleBean("Odonata", "", "Odonata"));
        rawTrapSampleList.add(new SampleBean("Opiliones", "", "Opiliones"));
        rawTrapSampleList.add(new SampleBean("Orthoptera", "", "Orthoptera"));
        rawTrapSampleList.add(new SampleBean("Phoridae", "", "Phoridae"));
        rawTrapSampleList.add(new SampleBean("Phthiraptera", "", "Phthiraptera"));
        rawTrapSampleList.add(new SampleBean("Plecoptera", "", "Plecoptera"));
        rawTrapSampleList.add(new SampleBean("Pseudoscorpionida", "", "Pseudoscorpionida"));
        rawTrapSampleList.add(new SampleBean("Psocoptera", "", "Psocoptera"));
        rawTrapSampleList.add(new SampleBean("Rhaphidioptera", "", "Rhaphidioptera"));
        rawTrapSampleList.add(new SampleBean("Sciaridae", "", "Sciaridae"));
        rawTrapSampleList.add(new SampleBean("Sepsidae", "", "Sepsidae"));
        rawTrapSampleList.add(new SampleBean("Siphonaptera", "", "Siphonaptera"));
        rawTrapSampleList.add(new SampleBean("Stenorrhyncha", "", "Stenorrhyncha"));
        rawTrapSampleList.add(new SampleBean("Strepsiptera", "", "Strepsiptera"));
        rawTrapSampleList.add(new SampleBean("Syrphidae", "", "Syrphidae"));
        rawTrapSampleList.add(new SampleBean("Thysanoptera", "", "Thysanoptera"));
        rawTrapSampleList.add(new SampleBean("Tipulidae", "", "Tipulidae"));
        rawTrapSampleList.add(new SampleBean("Trichoptera", "", "Trichoptera"));
        
        return rawTrapSampleList;
    }

    public static List<SampleBean> getBrachyceraList() {
        
        List<SampleBean> brachyceraList = new ArrayList<SampleBean>();
        
        brachyceraList.add(new SampleBean("Acartophthalmidae", "", "Acartophthalmidae"));
        brachyceraList.add(new SampleBean("Acroceridae", "", "Acroceridae"));
        brachyceraList.add(new SampleBean("Agromyzidae", "", "Agromyzidae"));
        brachyceraList.add(new SampleBean("Anthomyiidae", "", "Anthomyiidae"));
        brachyceraList.add(new SampleBean("Anthomyzidae", "", "Anthomyzidae"));
        brachyceraList.add(new SampleBean("Asilidae", "", "Asilidae"));
        brachyceraList.add(new SampleBean("Asteiidae", "", "Asteiidae"));
        brachyceraList.add(new SampleBean("Atelestidae", "", "Atelestidae"));
        brachyceraList.add(new SampleBean("Athericidae", "", "Athericidae"));
        brachyceraList.add(new SampleBean("Aulacigasteridae", "", "Aulacigasteridae"));
        brachyceraList.add(new SampleBean("Bombyliidae", "", "Bombyliidae"));
        brachyceraList.add(new SampleBean("Brachystomatidae", "", "Brachystomatidae"));
        brachyceraList.add(new SampleBean("Braulidae", "", "Braulidae"));
        brachyceraList.add(new SampleBean("Calliphoridae", "", "Calliphoridae"));
        brachyceraList.add(new SampleBean("Camillidae", "", "Camillidae"));
        brachyceraList.add(new SampleBean("Campichoetidae", "", "Campichoetidae"));
        brachyceraList.add(new SampleBean("Canacidae", "", "Canacidae"));
        brachyceraList.add(new SampleBean("Carnidae", "", "Carnidae"));
        brachyceraList.add(new SampleBean("Chamaemyiidae", "", "Chamaemyiidae"));
        brachyceraList.add(new SampleBean("Chiropteromyzidae", "", "Chiropteromyzidae"));
        brachyceraList.add(new SampleBean("Chloropidae", "", "Chloropidae"));
        brachyceraList.add(new SampleBean("Chyromyidae", "", "Chyromyidae"));
        brachyceraList.add(new SampleBean("Clusiidae", "", "Clusiidae"));
        brachyceraList.add(new SampleBean("Coelopidae", "", "Coelopidae"));
        brachyceraList.add(new SampleBean("Coenomyiidae", "", "Coenomyiidae"));
        brachyceraList.add(new SampleBean("Conopidae", "", "Conopidae"));
        brachyceraList.add(new SampleBean("Curtonotidae", "", "Curtonotidae"));
        brachyceraList.add(new SampleBean("Diastatidae", "", "Diastatidae"));
        brachyceraList.add(new SampleBean("Dolichopodidae", "", "Dolichopodidae"));
        brachyceraList.add(new SampleBean("Drosophilidae", "", "Drosophilidae"));
        brachyceraList.add(new SampleBean("Dryomyzidae", "", "Dryomyzidae"));
        brachyceraList.add(new SampleBean("Empididae", "", "Empididae"));
        brachyceraList.add(new SampleBean("Ephydridae", "", "Ephydridae"));
        brachyceraList.add(new SampleBean("Fanniidae", "", "Fanniidae"));
        brachyceraList.add(new SampleBean("Gasterophilidae", "", "Gasterophilidae"));
        brachyceraList.add(new SampleBean("Helcomyzidae", "", "Helcomyzidae"));
        brachyceraList.add(new SampleBean("Heleomyzidae", "", "Heleomyzidae"));
        brachyceraList.add(new SampleBean("Heterocheilidae", "", "Heterocheilidae"));
        brachyceraList.add(new SampleBean("Hilarimorphidae", "", "Hilarimorphidae"));
        brachyceraList.add(new SampleBean("Hippoboscidae", "", "Hippoboscidae"));
        brachyceraList.add(new SampleBean("Hybotidae", "", "Hybotidae"));
        brachyceraList.add(new SampleBean("Hypodermatidae", "", "Hypodermatidae"));
        brachyceraList.add(new SampleBean("Lauxaniidae", "", "Lauxaniidae"));
        brachyceraList.add(new SampleBean("Lonchaeidae", "", "Lonchaeidae"));
        brachyceraList.add(new SampleBean("Lonchopteridae", "", "Lonchopteridae"));
        brachyceraList.add(new SampleBean("Megamerinidae", "", "Megamerinidae"));
        brachyceraList.add(new SampleBean("Micropezidae", "", "Micropezidae"));
        brachyceraList.add(new SampleBean("Microphoridae", "", "Microphoridae"));
        brachyceraList.add(new SampleBean("Milichiidae", "", "Milichiidae"));
        brachyceraList.add(new SampleBean("Muscidae", "", "Muscidae"));
        brachyceraList.add(new SampleBean("Mydidae", "", "Mydidae"));
        brachyceraList.add(new SampleBean("Mythicomyiidae", "", "Mythicomyiidae"));
        brachyceraList.add(new SampleBean("Nemestrinidae", "", "Nemestrinidae"));
        brachyceraList.add(new SampleBean("Nycteribiidae", "", "Nycteribiidae"));
        brachyceraList.add(new SampleBean("Odiniidae", "", "Odiniidae"));
        brachyceraList.add(new SampleBean("Oestridae", "", "Oestridae"));
        brachyceraList.add(new SampleBean("Opetiidae", "", "Opetiidae"));
        brachyceraList.add(new SampleBean("Opomyzidae", "", "Opomyzidae"));
        brachyceraList.add(new SampleBean("Otitinae", "", "Otitinae"));
        brachyceraList.add(new SampleBean("Pallopteridae", "", "Pallopteridae"));
        brachyceraList.add(new SampleBean("Periscelididae", "", "Periscelididae"));
        brachyceraList.add(new SampleBean("Piophilidae", "", "Piophilidae"));
        brachyceraList.add(new SampleBean("Pipunculidae", "", "Pipunculidae"));
        brachyceraList.add(new SampleBean("Platypezidae", "", "Platypezidae"));
        brachyceraList.add(new SampleBean("Platystomatidae", "", "Platystomatidae"));
        brachyceraList.add(new SampleBean("Pseudopomyzidae", "", "Pseudopomyzidae"));
        brachyceraList.add(new SampleBean("Psilidae", "", "Psilidae"));
        brachyceraList.add(new SampleBean("Pyrgotidae", "", "Pyrgotidae"));
        brachyceraList.add(new SampleBean("Rhagionidae", "", "Rhagionidae"));
        brachyceraList.add(new SampleBean("Rhinophoridae", "", "Rhinophoridae"));
        brachyceraList.add(new SampleBean("Sarcophagidae", "", "Sarcophagidae"));
        brachyceraList.add(new SampleBean("Scatophagidae", "", "Scatophagidae"));
        brachyceraList.add(new SampleBean("Scenopinidae", "", "Scenopinidae"));
        brachyceraList.add(new SampleBean("Sciomyzidae", "", "Sciomyzidae"));
        brachyceraList.add(new SampleBean("Sphaeroceridae", "", "Sphaeroceridae"));
        brachyceraList.add(new SampleBean("Stenomicridae", "", "Stenomicridae"));
        brachyceraList.add(new SampleBean("Stratiomyidae", "", "Stratiomyidae"));
        brachyceraList.add(new SampleBean("Strongylophthalmyiidae", "", "Strongylophthalmyiidae"));
        brachyceraList.add(new SampleBean("Tabanidae", "", "Tabanidae"));
        brachyceraList.add(new SampleBean("Tachinidae", "", "Tachinidae"));
        brachyceraList.add(new SampleBean("Tanypezidae", "", "Tanypezidae"));
        brachyceraList.add(new SampleBean("Tephritidae", "", "Tephritidae"));
        brachyceraList.add(new SampleBean("Tethinidae", "", "Tethinidae"));
        brachyceraList.add(new SampleBean("Therevidae", "", "Therevidae"));
        brachyceraList.add(new SampleBean("Trixoscelidae", "", "Trixoscelidae"));
        brachyceraList.add(new SampleBean("Ulidiidae", "", "Ulidiidae"));
        brachyceraList.add(new SampleBean("Vermileonidae", "", "Vermileonidae"));
        brachyceraList.add(new SampleBean("Xylomyidae", "", "Xylomyidae"));
        brachyceraList.add(new SampleBean("Xylophagidae", "", "Xylophagidae"));
        
        return brachyceraList;
    }

    public static List<SampleBean> getBraconidaeList() {
        
        List<SampleBean> braconidaeList = new ArrayList<SampleBean>();
        
        braconidaeList.add(new SampleBean("Adelinae", "", "Adelinae"));
        braconidaeList.add(new SampleBean("Agathidinae", "", "Agathidinae"));
        braconidaeList.add(new SampleBean("Alysiinae", "", "Alysiinae"));
        braconidaeList.add(new SampleBean("Aphidiinae", "", "Aphidiinae"));
        braconidaeList.add(new SampleBean("Blacinae", "", "Blacinae"));
        braconidaeList.add(new SampleBean("Braconinae", "", "Braconinae"));
        braconidaeList.add(new SampleBean("Cardiochilinae", "", "Cardiochilinae"));
        braconidaeList.add(new SampleBean("Cenocoeliinae", "", "Cenocoeliinae"));
        braconidaeList.add(new SampleBean("Charmontinae", "", "Charmontinae"));
        braconidaeList.add(new SampleBean("Cheloninae", "", "Cheloninae"));
        braconidaeList.add(new SampleBean("Doryctinae", "", "Doryctinae"));
        braconidaeList.add(new SampleBean("Euphorinae", "", "Euphorinae"));
        braconidaeList.add(new SampleBean("Exothecinae", "", "Exothecinae"));
        braconidaeList.add(new SampleBean("Gnamptodontinae", "", "Gnamptodontinae"));
        braconidaeList.add(new SampleBean("Helconinae", "", "Helconinae"));
        braconidaeList.add(new SampleBean("Histeromerinae", "", "Histeromerinae"));
        braconidaeList.add(new SampleBean("Homolobinae", "", "Homolobinae"));
        braconidaeList.add(new SampleBean("Hormiinae", "", "Hormiinae"));
        braconidaeList.add(new SampleBean("Ichneutinae", "", "Ichneutinae"));
        braconidaeList.add(new SampleBean("Macrocentrinae", "", "Macrocentrinae"));
        braconidaeList.add(new SampleBean("Meteorinae", "", "Meteorinae"));
        braconidaeList.add(new SampleBean("Microgasterinae", "", "Microgasterinae"));
        braconidaeList.add(new SampleBean("Miracinae", "", "Miracinae"));
        braconidaeList.add(new SampleBean("Neoneurinae", "", "Neoneurinae"));
        braconidaeList.add(new SampleBean("Opiinae", "", "Opiinae"));
        braconidaeList.add(new SampleBean("Orgilinae", "", "Orgilinae"));
        braconidaeList.add(new SampleBean("Pambolinae", "", "Pambolinae"));
        braconidaeList.add(new SampleBean("Proteropinae", "", "Proteropinae"));
        braconidaeList.add(new SampleBean("Rhyssalinae", "", "Rhyssalinae"));
        braconidaeList.add(new SampleBean("Rogadinae", "", "Rogadinae"));
        braconidaeList.add(new SampleBean("Sigalphinae", "", "Sigalphinae"));
        
        return braconidaeList;
    }

    public static List<SampleBean> getChalcidoideaList() {
        
        List<SampleBean> chalcidoideaList = new ArrayList<SampleBean>(); 
        
        chalcidoideaList.add(new SampleBean("Aphelinidae", "", "Aphelinidae"));
        chalcidoideaList.add(new SampleBean("Chalcididae", "", "Chalcididae"));
        chalcidoideaList.add(new SampleBean("Encyrtidae", "", "Encyrtidae"));
        chalcidoideaList.add(new SampleBean("Eulophidae", "", "Eulophidae"));
        chalcidoideaList.add(new SampleBean("Eupelmidae", "", "Eupelmidae"));
        chalcidoideaList.add(new SampleBean("Eurytomidae", "", "Eurytomidae"));
        chalcidoideaList.add(new SampleBean("Mymar pulchellum", "", "Mymar pulchellum"));
        chalcidoideaList.add(new SampleBean("Mymar regale", "", "Mymar regale"));
        chalcidoideaList.add(new SampleBean("Mymaridae", "", "Mymaridae"));
        chalcidoideaList.add(new SampleBean("Ormyridae", "", "Ormyridae"));
        chalcidoideaList.add(new SampleBean("Perilampidae", "", "Perilampidae"));
        chalcidoideaList.add(new SampleBean("Pteromalidae", "", "Pteromalidae"));
        chalcidoideaList.add(new SampleBean("Sighiphoridae", "", "Sighiphoridae"));
        chalcidoideaList.add(new SampleBean("Tetracampidae", "", "Tetracampidae"));
        chalcidoideaList.add(new SampleBean("Torymidae", "", "Torymidae"));
        chalcidoideaList.add(new SampleBean("Trichogrammatidae", "", "Trichogrammatidae"));
        
        return chalcidoideaList;
    }

    public static List<SampleBean> getHymenopteraList() {
        
        List<SampleBean> hymenopteraList = new ArrayList<SampleBean>();
        
        hymenopteraList.add(new SampleBean("Apoidea", "", "Apoidea"));
        hymenopteraList.add(new SampleBean("Bethylidae", "", "Bethylidae"));
        hymenopteraList.add(new SampleBean("Braconidae", "", "Braconidae"));
        hymenopteraList.add(new SampleBean("Ceraphronidae", "", "Ceraphronidae"));
        hymenopteraList.add(new SampleBean("Chalcidoidea", "", "Chalcidoidea"));
        hymenopteraList.add(new SampleBean("Chrysididae", "", "Chrysididae"));
        hymenopteraList.add(new SampleBean("Cynipoidea", "", "Cynipoidea"));
        hymenopteraList.add(new SampleBean("Diapriidae", "", "Diapriidae"));
        hymenopteraList.add(new SampleBean("Dryinidae", "", "Dryinidae"));
        hymenopteraList.add(new SampleBean("Embolemidae", "", "Embolemidae"));
        hymenopteraList.add(new SampleBean("Eumeninae", "", "Eumeninae"));
        hymenopteraList.add(new SampleBean("Evaniidae", "", "Evaniidae"));
        hymenopteraList.add(new SampleBean("Formicidae", "", "Formicidae"));
        hymenopteraList.add(new SampleBean("Gasteruptidae", "", "Gasteruptidae"));
        hymenopteraList.add(new SampleBean("Heloridae", "", "Heloridae"));
        hymenopteraList.add(new SampleBean("Ichneumonidae", "","Ichneumonidae"));
        hymenopteraList.add(new SampleBean("Mymaromma", "", "Mymaromma"));
        hymenopteraList.add(new SampleBean("Mymarommatidae", "", "Mymarommatidae"));
        hymenopteraList.add(new SampleBean("Mymarommatoidea", "", "Mymarommatoidea"));
        hymenopteraList.add(new SampleBean("Platygastridae", "", "Platygastridae"));
        hymenopteraList.add(new SampleBean("Pompilidae", "", "Pompilidae"));
        hymenopteraList.add(new SampleBean("Proctotrupidae", "", "Proctotrupidae"));
        hymenopteraList.add(new SampleBean("Scelionidae", "", "Scelionidae"));
        hymenopteraList.add(new SampleBean("Sphecidae", "", "Sphecidae"));
        hymenopteraList.add(new SampleBean("Symphyta", "", "Symphyta"));
        hymenopteraList.add(new SampleBean("Tiphiidae", "", "Tiphiidae"));
        hymenopteraList.add(new SampleBean("Vespidae", "", "Vespidae"));
        
        return hymenopteraList;
    }

    public static List<SampleBean> getIchneumonidaeList() {
        
        List<SampleBean> ichneumonidaeList = new ArrayList<SampleBean>();
        
        ichneumonidaeList.add(new SampleBean("Acaenitinae", "", "Acaenitinae"));
        ichneumonidaeList.add(new SampleBean("Adelognathinae", "", "Adelognathinae"));
        ichneumonidaeList.add(new SampleBean("Alomyiinae", "", "Alomyiinae"));
        ichneumonidaeList.add(new SampleBean("Anomaloninae", "", "Anomaloninae"));
        ichneumonidaeList.add(new SampleBean("Atrophini", "", "Atrophini"));
        ichneumonidaeList.add(new SampleBean("Banchinae", "", "Banchinae"));
        ichneumonidaeList.add(new SampleBean("Banchini", "", "Banchini"));
        ichneumonidaeList.add(new SampleBean("Brachycyrtinae", "", "Brachycyrtinae"));
        ichneumonidaeList.add(new SampleBean("Campopleginae", "", "Campopleginae"));
        ichneumonidaeList.add(new SampleBean("Collyriinae", "", "Collyriinae"));
        ichneumonidaeList.add(new SampleBean("Cremastinae", "", "Cremastinae"));
        ichneumonidaeList.add(new SampleBean("Cryptinae", "", "Cryptinae"));
        ichneumonidaeList.add(new SampleBean("Cryptini", "", "Cryptini"));
        ichneumonidaeList.add(new SampleBean("Ctenopelmatinae", "", "Ctenopelmatinae"));
        ichneumonidaeList.add(new SampleBean("Ctenopelmatini", "", "Ctenopelmatini"));
        ichneumonidaeList.add(new SampleBean("Cylloceriinae", "", "Cylloceriinae"));
        ichneumonidaeList.add(new SampleBean("Diacritinae", "", "Diacritinae"));
        ichneumonidaeList.add(new SampleBean("Diplazontinae", "", "Diplazontinae"));
        ichneumonidaeList.add(new SampleBean("Ephialtini", "", "Ephialtini"));
        ichneumonidaeList.add(new SampleBean("Eucerotinae", "", "Eucerotinae"));
        ichneumonidaeList.add(new SampleBean("Euryproctini", "", "Euryproctini"));
        ichneumonidaeList.add(new SampleBean("Exenterini", "", "Exenterini"));
        ichneumonidaeList.add(new SampleBean("Glyptini", "", "Glyptini"));
        ichneumonidaeList.add(new SampleBean("Hemigastrini", "", "Hemigastrini"));
        ichneumonidaeList.add(new SampleBean("Hybrizontinae", "", "Hybrizontinae"));
        ichneumonidaeList.add(new SampleBean("Ichneumonini", "", "Ichneumonini"));
        ichneumonidaeList.add(new SampleBean("Lycorininae", "", "Lycorininae"));
        ichneumonidaeList.add(new SampleBean("Mesochorinae", "", "Mesochorinae"));
        ichneumonidaeList.add(new SampleBean("Mesoleini", "", "Mesoleini"));
        ichneumonidaeList.add(new SampleBean("Metopiinae", "", "Metopiinae"));
        ichneumonidaeList.add(new SampleBean("Microleptinae", "", "Microleptinae"));
        ichneumonidaeList.add(new SampleBean("Neorhacodinae", "", "Neorhacodinae"));
        ichneumonidaeList.add(new SampleBean("Netelia", "", "Netelia"));
        ichneumonidaeList.add(new SampleBean("Oedemopsini", "", "Oedemopsini"));
        ichneumonidaeList.add(new SampleBean("Ophioninae", "", "Ophioninae"));
        ichneumonidaeList.add(new SampleBean("Orthocentrinae", "", "Orthocentrinae"));
        ichneumonidaeList.add(new SampleBean("Orthopemathinae", "", "Orthopemathinae"));
        ichneumonidaeList.add(new SampleBean("Oxytorinae", "", "Oxytorinae"));
        ichneumonidaeList.add(new SampleBean("Perilissini", "", "Perilissini"));
        ichneumonidaeList.add(new SampleBean("Phaeogenini", "", "Phaeogenini"));
        ichneumonidaeList.add(new SampleBean("Phrudinae", "", "Phrudinae"));
        ichneumonidaeList.add(new SampleBean("Phygadeuontini", "", "Phygadeuontini"));
        ichneumonidaeList.add(new SampleBean("Phytodietini", "", "Phytodietini"));
        ichneumonidaeList.add(new SampleBean("Pimplinae", "", "Pimplinae"));
        ichneumonidaeList.add(new SampleBean("Pimplini", "", "Pimplini"));
        ichneumonidaeList.add(new SampleBean("Pionini", "", "Pionini"));
        ichneumonidaeList.add(new SampleBean("Poemeniinae", "", "Poemeniinae"));
        ichneumonidaeList.add(new SampleBean("Polysphinctini", "", "Polysphinctini"));
        ichneumonidaeList.add(new SampleBean("Rhyssinae", "", "Rhyssinae"));
        ichneumonidaeList.add(new SampleBean("Scolobatini", "", "Scolobatini"));
        ichneumonidaeList.add(new SampleBean("Stilbopinae", "", "Stilbopinae"));
        ichneumonidaeList.add(new SampleBean("Tersilochinae", "", "Tersilochinae"));
        ichneumonidaeList.add(new SampleBean("Tryphoninae", "", "Tryphoninae"));
        ichneumonidaeList.add(new SampleBean("Tryphonini", "", "Tryphonini"));
        ichneumonidaeList.add(new SampleBean("Xoridinae", "", "Xoridinae"));
        
        return ichneumonidaeList;
    }

    public static List<SampleBean> getNematoceraList() {
        
        List<SampleBean> nematoceraList = new ArrayList<SampleBean>();
        
        nematoceraList.add(new SampleBean("Anisopodidae", "", "Anisopodidae"));
        nematoceraList.add(new SampleBean("Bibionidae", "", "Bibionidae"));
        nematoceraList.add(new SampleBean("Canthyloscelidae", "", "Canthyloscelidae"));
        nematoceraList.add(new SampleBean("Cecidomyidae", "", "Cecidomyidae"));
        nematoceraList.add(new SampleBean("Ceratopogonidae", "", "Ceratopogonidae"));
        nematoceraList.add(new SampleBean("Chaboridae", "", "Chaboridae"));
        nematoceraList.add(new SampleBean("Chironomidae", "", "Chironomidae"));
        nematoceraList.add(new SampleBean("Culicidae", "", "Culicidae"));
        nematoceraList.add(new SampleBean("Dixidae", "", "Dixidae"));
        nematoceraList.add(new SampleBean("Mycetobiidae", "", "Mycetobiidae"));
        nematoceraList.add(new SampleBean("Mycetophilidae", "", "Mycetophilidae"));
        nematoceraList.add(new SampleBean("Pachyneuridae", "", "Pachyneuridae"));
        nematoceraList.add(new SampleBean("Psychodidae", "", "Psychodidae"));
        nematoceraList.add(new SampleBean("Ptychoteridae", "", "Ptychoteridae"));
        nematoceraList.add(new SampleBean("Scatopsoidae", "", "Scatopsoidae"));
        nematoceraList.add(new SampleBean("Sciaridae", "", "Sciaridae"));
        nematoceraList.add(new SampleBean("Simuliidae", "", "Simuliidae"));
        nematoceraList.add(new SampleBean("Synnereuridae", "", "Synnereuridae"));
        nematoceraList.add(new SampleBean("Thaumaleidae", "", "Thaumaleidae"));
        nematoceraList.add(new SampleBean("Tipuloidea", "", "Tipuloidea"));
        nematoceraList.add(new SampleBean("Trichoceridae", "", "Trichoceridae"));
        
        return nematoceraList;
    }  
}
