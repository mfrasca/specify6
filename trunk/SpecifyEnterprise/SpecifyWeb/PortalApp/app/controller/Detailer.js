Ext.define('SpWebPortal.controller.Detailer', {
    extend: 'Ext.app.Controller',

    //localizable text...
    detailPopupTitle: 'Detail',
    //...localizable text

    requires: [
	'SpWebPortal.view.DetailPanel',
	'SpWebPortal.view.DetailsPanel'
    ],

    init: function() {
	console.info("Detailer.init");
	this.control({
	    'actioncolumn[itemid="detail-popup-ctl"]': {
		clicked: this.onGridDetailClk
	    },
	    'spmaingrid': {
		itemdblclick: this.onGridDblClk,
		googlemarkerclick: this.onGoogleMarkerClick
	    },
	    'spthumbnail': {
		itemdblclick: this.onThumbDblClk
	    },
	    '#spwpmainmappane': {
		googlemarkerclick: this.onGoogleMarkerClick
	    },
	    'pagingtoolbar[itemid="spwpdetailpager"]': {
		change: this.onDetailsPageChange
	    }
	});

	this.callParent(arguments);
    },

    onGoogleMarkerClick: function(record) {
	//console.info("Detailer.onGoogleMarkerClick");
	if (record instanceof Array) {
	    if (record.length > 1) {
		this.popupDetails(record, false);
	    } else if (record.length == 1) {
		this.popupDetail(record[0], false);
	    }
	} else {
	    this.popupDetail(record, false);
	}
    },

    onGridDetailClk: function(record, isDetailGrid, rowIndex) {
	//console.info('grid detail clicked -- ' + arguments);
	if (isDetailGrid) {
	    this.showDetailForm(rowIndex);
	} else {
	    this.popupDetail(record, true);
	}
    },

    onGridDblClk: function(dataview, record, item, rowIndex) {
	//console.info(record);
	var grid = dataview.up('spmaingrid');
	if (grid.getIsDetail()) {
	    this.showDetailForm(rowIndex);
	} else {
	    this.popupDetail(record, true);
	}
    },

    onThumbDblClk: function(thumbnailer, record) {
	console.info("thumb dbl-clicked");
	console.info(arguments);
	var attacheeIDs = [];
	//var attacheeData = record.get('AttachedTo');
	//when 'id' is re-named then use above. (See notes in app.js where MainModel is built.
	var attacheeData = record.get('AttachedToDescr');
	//eventually attacheData will be json list of attachees,
	//just one for now
	attacheeIDs[0] = attacheeData;
	var attachees = [attacheeIDs.length];
	var theStore = Ext.getStore('MainSolrStore');
	for (var a = 0; a < attachees.length; a++) {
	    attachees[a] = theStore.getById(attacheeIDs[a]);
	}
	if (attachees.length > 0) {
	    //this is lame. One popup func!
	    if (attachees.length == 1) {
		this.popupDetail(attachees[0], true);
	    } else {
		this.popupDetails(attachees, true);
	    }
	}
    },

    onDetailsPageChange: function() {
	//console.info("Detailer.onDetailsPageChange()");
	this.detailsForm.loadCurrentRecord();
    },

    popupDetails: function(records, showMap) {
	if (this.detailsPopupWin == null) {
	    this.detailsForm = Ext.widget('spdetailspanel', {
		showMap: showMap
	    });
	    this.detailsPopupWin = Ext.create('Ext.window.Window', {
		title: this.detailPopupTitle,
		height: 600,
		width: 800,
		maximizable: false,
		resizable: true,
		closeAction: 'hide',
		layout: 'fit',
		items: [
		    this.detailsForm
		]
	    });
	    this.detailsPopupWin.setPosition(1,1);
	}
	if (this.detailPopupWin != null && this.detailPopupWin.isVisible()) {
	    //probably can just put panels on same form!
	    this.detailsPopupWin.setPosition(this.detailPopupWin.getPosition());
	    this.detailsPopupWin.setHeight(this.detailPopupWin.getHeight());
	    this.detailsPopupWin.setWidth(this.detailPopupWin.getWidth());
	    this.detailPopupWin.hide();
	}
	this.detailsForm.loadRecords(records);
	this.detailsPopupWin.show();
	this.detailsPopupWin.toFront();
    },
	
    popupDetail: function(record, showMap) {
	if (this.detailPopupWin == null) {
	    this.detailForm = Ext.widget('spdetailpanel', {
		showMap: showMap
	    });
	    this.detailPopupWin = Ext.create('Ext.window.Window', {
		title: this.detailPopupTitle,
		height: 600,
		width: 800,
		maximizable: false,
		resizable: true,
		closeAction: 'hide',
		layout: 'fit',
		items: [
		    this.detailForm
		]
	    });
	    this.detailPopupWin.setPosition(1,1);
	}
	if (this.detailsPopupWin != null && this.detailsPopupWin.isVisible()) {
	    //probably can just put panels on same form!
	    this.detailPopupWin.setPosition(this.detailsPopupWin.getPosition());
	    this.detailPopupWin.setHeight(this.detailsPopupWin.getHeight());
	    this.detailPopupWin.setWidth(this.detailsPopupWin.getWidth());
	    this.detailsPopupWin.hide();
	}
	this.detailForm.loadRecord(record);
	this.detailPopupWin.show();
	this.detailPopupWin.toFront();
    },

    showDetailForm: function(rowIndex) {
	if (this.detailsPopupWin != null && this.detailsPopupWin.isVisible() && this.detailsForm != null) {
	    var tab = this.detailsForm.down('tabpanel');
	    var detailPager = this.detailsForm.down('pagingtoolbar');
	    detailPager.getStore().loadPage(rowIndex+1);
	    tab.setActiveTab(1); 
	} else {
	    //something strange has happened
	    console.info("Detailer: unable to show detail form on a details popup because the details popup has evaporated.");
	}
    },

    detailPopupWin: null,
    detailForm: null

});