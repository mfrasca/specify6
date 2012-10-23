Ext.define('SpWebPortal.view.ImageSingleView', {
    extend: 'Ext.panel.Panel',
    xtype: 'spimagesingleview',
    alias: 'widget.imagesingleview',

    layout: 'border',

    //localizable text...
    specimenBtnTxt: 'Associated Specimens',
    specimenBtnTip: 'View specimen(s) associated with this image',
    imageDetailBtnTxt: 'Image Details',
    imageDetailBtnTip: 'View image details',
    viewActualBtnText: 'Actual size',
    viewStdBtnText: 'Standard size',
    viewActualBtnTip: 'Click to view standard size',
    viewStdBtnTip: 'Click to view actual size',
    //...localizable text

    config: {	
	imageRecord: null,
	isActualSize: false
    },
    
    initComponent: function() {
	var cmps = [];
	cmps[0] = Ext.create('Ext.panel.Panel', {
	    region: 'center',
	    itemid: 'image-panel',
	    html: this.getImgHtml(),
	    autoScroll: true,
	});

	var dcmps = [];
	dcmps[0] = Ext.create('Ext.toolbar.Toolbar', {
	    dock: 'bottom',
	    items: [
		{
		    xtype: 'button',
		    id: 'spwp-img-single-viewsize-btn',
		    text: this.isActualSize ? this.viewActualBtnText : this.viewStdBtnText,
		    tooltip: this.isActualSize ? this.viewActualBtnTip : this.viewStdBtnTip
		},
		{
		    xtype: 'button',
		    text: this.specimenBtnTxt,
		    tooltip: this.specimenBtnTip,
		    id: 'spwp-img-single-specimenbtn'
		},
		{
		    xtype: 'button',
		    text: this.imageDetailBtnTxt,
		    tooltip: this.imageDetailBtnTip,
		    id: 'spwp-img-single-imagedetailbtn'
		}
	    ]
	});

	this.items = cmps;
	this.dockedItems = dcmps;
	
	this.callParent(arguments);
    },

    getImgHtml: function() {
	var src = this.getIsActualSize() ? this.getImageRecord().get('Src') : this.getImageRecord().get('StdSrc');
	return  '<table class="deadcenter"> <tr><td><img src='+  src + '></td></tr></table>'; //caller will have filled-in the src.
    },

});
