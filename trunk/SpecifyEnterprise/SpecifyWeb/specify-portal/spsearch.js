Ext.require([
             'Ext.grid.*',
             'Ext.data.*',
             'Ext.util.*',
             'Ext.toolbar.Paging',
             'Ext.ModelManager',
             'Ext.tip.QuickTipManager',
             'Ext.tab.*'
]);

//-------------------------------------------------
//
//-------------------------------------------------
function fixValueForSearch(text, prefix)
{
    var searchStr = "";
    
    if (text != null && text.length > 0)
    {
        var tokens = text.split(" ");
        for (i=0;i<tokens.length;i++)
        {
            if (i > 0) searchStr += "+";
            var str = tokens[i].toLowerCase();
            
            if (str == "and")
            {
                searchStr += "AND";
                
            } else if (str == "or")
            {
                searchStr += "OR";
                
            } else
            {
                searchStr += prefix == null ? str : (prefix + ":" + str);
            }
        }
    }
    return searchStr;
}
renderColumn
//-------------------------------------------------
//
//-------------------------------------------------
function doSearch(searchText)
{
    /*var resultsGridDiv   = $('#resultsGrid');
    var text = fixValueForSearch($('#search_text').val(), null);
    resultsGridDiv.dataTable().fnClearTable();
    var sqlStr = sql + "&q=" + text;
    */
	//var text = document.getElementById('search-text').value;
	alert(searchText);
}

Ext.onReady(function() {
	
	Ext.tip.QuickTipManager.init();
	
  Ext.define('SearchModel', {
    extend: 'Ext.data.Model',
    fields: [
       {name: 'id',  type: 'int'},
       {name: 'cn',  type: 'string'},
       {name: 'yr',  type: 'string'}
    ],
    idProperty: 'id'
});

  //        url: 'http://boxley.nhm.ku.edu:8983/solr/select?indent=on&version=2.2&fq=&start=0&rows=50&fl=*%2Cscore&qt=&wt=json&explainOther=&hl.fl=&q=iowa',

var store = Ext.create('Ext.data.Store', {
    pageSize: 100,
    //id: 'store',
    model: 'SearchModel',
    proxy: {
       type: 'jsonp',
       callbackKey: 'json.wrf',
       url: 'http://boxley.nhm.ku.edu:8983/solr/select?indent=on&version=2.2&fq=&rows=100&fl=*%2Cscore&qt=&wt=json&explainOther=&hl.fl=&q=california',
   reader: {
      root: 'response.docs',
      totalProperty: 'response.numFound'
   }
 }
});

function renderTitle(value, p, record) 
{
   return Ext.String.format('<a href="{1}" target="_blank">{0}</a>',
       value,
       record.data.link
       );
}

//      0            1              2                3               4           5           6          7               8         9          10        11             12
// CatalogNumber, StartDate, StationFieldNumber TypeStatusName, tx.FullName, Latitude1, Longitude1, LocalityName, geo.FullName, LastName, FirstName, MiddleInitial, Image Name

function renderColumn(value, metaData, record, rowIndex, colIndex, store) 
{
	var valsArray = record.raw.valsArray;
	if (typeof valsArray === "undefined")
	{
		record.raw.valsArray = record.raw.contents.split( "\t" );
		valsArray = record.raw.valsArray;
	}
	var arrayInx = colIndex - 1;
	
	if (arrayInx > 2) 
	{
		arrayInx++;
	}
	
    return valsArray[arrayInx];
}

function renderCatNum(value, p, record) 
{
   var catNum = value.replace(/^0+/, '');
   return catNum;
}

var tabs = Ext.createWidget('tabpanel', {
    renderTo: 'tabs1',
    width: 1024,
    height: 600,
    activeTab: 0,
    defaults :{
        bodyPadding: 1
    },
    items: [{
        contentEl:'script', 
        title: 'Search',
        closable: true
    },{renderColumn
        contentEl:'markup', 
        title: 'Advanced Search'
    }]
});


var grid = Ext.create('Ext.grid.Panel', {
   width: 1020,
   height: 550,
   autoHeight: true,
   title: 'KU Fish',
   store: store,
   loadMask: true,
   disableSelection: false,
   invalidateScrollerOnRefresh: false,
   viewConfig: {
     trackOver: true,
     forceFit:true
   },
   // grid columns
   columns:[{
      xtype: 'rownumberer',
      width: 40,
      sortable: false
   },/*{
      id: 'id',
      text: "Id",
      dataIndex: 'id',
      flex: 1,
      renderer: renderTitle,
      sortable: false
    //    0            1              2                3               4           5           6          7               8         9          10        11             12
    //CatalogNumber, StartDate, StationFieldNumber TypeStatusName, tx.FullName, Latitude1, Longitude1, LocalityName, geo.FullName, LastName, FirstName, MiddleInitial, Image Name
   },*/{
	      id: 'cn',
	      text: "Cat. Number",
	      dataIndex: 'cn',
	      width: 80,
	      renderer: renderCatNum,
	      sortable: true
   },{
	      text: "Start Date",
	      dataIndex: 'yr',
	      width: 80,
	      renderer: renderColumn,
	      sortable: true
   },{
	      text: "Field Number",
	      dataIndex: 'yr',
	      width: 100,
	      renderer: renderColumn,
	      sortable: true
   },{
	      text: "Taxa",
	      dataIndex: 'yr',
	      width: 150,
	      renderer: renderColumn,
	      sortable: true
   },{
	      text: "Latitude",
	      dataIndex: 'yr',
	      width: 100,
	      renderer: renderColumn,
	      sortable: true
   },{
	      text: "Longitude",
	      dataIndex: 'yr',
	      width: 100,
	      renderer: renderColumn,
	      sortable: true
   },{
	      text: "Locality",
	      dataIndex: 'yr',
	      width: 130,
	      renderer: renderColumn,
	      sortable: true
   },{
	      text: "Geography",
	      dataIndex: 'yr',
	      width: 120,
	      renderer: renderColumn,
	      sortable: true
   },{
	      text: "Collector",
	      dataIndex: 'yr',
	      width: 100,
	      renderer: renderColumn,
	      sortable: true
    }
  	],
    bbar: Ext.create('Ext.PagingToolbar', {
        store: store,
        displayInfo: true,
        displayMsg: 'Displaying topics {0} - {1} of {2}',
        emptyMsg: "No topics to display"
    }),
   renderTo: 'search-grid'
});

var simple1 = Ext.create('Ext.form.Panel', {
    bodyStyle:'padding:5px 5px 0',
    height: 30,
    width: 'fit',
    layout: {
        align: 'stretch',
        type: 'hbox'
    },
    fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 75
    },
    defaultType: 'textfield',
   defaults: {
        anchor: '100%'
    },

    items: [{
        fieldLabel: 'Text',
        labelAlign: 'right',
        allowBlank:false,
        name: 'search-text',
        id: 'search-text'
    },
    {
        xtype: 'button',
        text: 'Search',
        handler: function() {
    	   var searchText = Ext.getDom('search-text').value;
    	   alert(simple1.query('#search-text'));
    	   //doSearch(searchText);
    	}
    }
    ]
});

simple1.render('search-form');

// trigger the data store load
//store.load();
store.loadPage(1);
});
