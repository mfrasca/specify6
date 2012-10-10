Ext.define('SpWebPortal.controller.ExpressSearch', {
    extend: 'SpWebPortal.controller.Search',
    
    refs: [
	{
	    ref: 'search',
	    selector: 'expressSrch'
	}
    ],

    stores: ['MainSolrStore'],
    models: ['MainModel'],

    init: function() {
	console.info("ExpressSearch.init");

	this.control({
	    'expressSrch button[itemid="search-btn"]': {
		click: this.doSearch
	    },
	    'expressSrch radiogroup[itemid="match-radio-grp"]': {
		change: this.matchAllChange
	    },
	    'expressSrch textfield': {
		specialkey: this.onSpecialKey
	    },
	    'button[itemid="mapsearchbtn"]': {
		click: this.onMapSearchClick
	    }
	});
	this.callParent(arguments);
    },

    onMapSearchClick: function() {
	if (!Ext.getCmp('spwpmainexpresssrch').getCollapsed()) {
	    this.setForceFitToMap(true);
	    this.doSearch();
	    this.setForceFitToMap(false);
	}
    },

    doSearch: function() {
	console.info("ExpressSearch doSearch()");
    	var search = this.getSearch();
	var control = search.query('textfield[itemid="search-text"]');
	var solr = this.getMainSolrStoreStore();
	var images = this.getRequireImages();
	var maps = this.getRequireGeoCoords();
	var mainQ = (typeof control[0].value === "undefined" || control[0].value == null || control[0].value == '') 
	    ? '*' 
	    : this.escapeForSolr(control[0].value);
	var filterToMap = (this.getForceFitToMap() || this.getFitToMap()) && this.mapViewIsActive();

	var url = solr.getExpressSearchUrl(images, maps, mainQ, filterToMap, this.getMatchAll());

	this.setForceFitToMap(false);

	solr.getProxy().url = url; 
	solr.setSearched(true);
	solr.loadPage(1);

	Ext.getCmp('spwpmaintabpanel').fireEvent('dosearch');
    }
});

