sap.ui.define(["sap/ui/core/mvc/Controller","sap/ui/core/format/NumberFormat","sap/ui/core/format/DateFormat"],function(t,e,a){"use strict";var r={style:"short",decimals:1,shortDecimals:2};var o=e.getFloatInstance(r);var s=a.getDateInstance({pattern:"MMM d, y"});return t.extend("ec.laurier.imad.controller.Dashboard",{onInit:function(){this.populateTotalStockValue();this.populateTotalStockValueByCategory();this.populateTop5ProductsSold();this.populateOverviewStockingIssues();this.populateCurrentStateOfStock();this.populateCombinedPercentageHistory()},populateTotalStockValue:function(){var t=this.getView().byId("totalStockValue");var e=this.getView().getModel("cardModel");var a=e.getProperty("/totalStockValue");var r;var c=[];var n=[];$.when($.ajax({url:"/imad-rs/rest/card1",dataType:"json",success:function(t){r=t.results.header;c=t.results.list;n=t.results.measures}})).then(function(){if(r!==null&&c.length>0&&n.length>0){r.n="$"+o.format(r.n);a["sap.card"].header.title="Total Stock value";a["sap.card"].header.details="as of "+s.format(new Date(r.details));a["sap.card"].header.data.json=r;a["sap.card"].content.data.json.list=c;a["sap.card"].content.measures=n;e.setProperty("/totalStockValue",a);t.refresh()}})},populateTotalStockValueByCategory:function(){var t=this.getView().byId("totalStockValueByCategory");var e=this.getView().getModel("cardModel");var a=e.getProperty("/totalStockValueByCategory");var r=[];$.ajax({url:"/imad-rs/rest/card2",dataType:"json",success:function(o){r=o.results;if(r.length>0){a["sap.card"].header.title="Total Stock by Category";a["sap.card"].content.data.json.measures=r;e.setProperty("/totalStockValueByCategory",a);t.refresh()}}})},populateTop5ProductsSold:function(){var t=this.getView().byId("top5ProductsSold");var e=this.getView().getModel("cardModel");var a=e.getProperty("/top5ProductsSold");var r=[];$.ajax({url:"/imad-rs/rest/card3",dataType:"json",success:function(o){r=o.results;if(r.length>0){a["sap.card"].header.title="Stock Value of Top 5 Products";a["sap.card"].content.data.json.list=r;e.setProperty("/top5ProductsSold",a);t.refresh()}}})},populateOverviewStockingIssues:function(){var t=this.getView().byId("overviewStockingIssues");var e=this.getView().getModel("cardModel");var a=e.getProperty("/overviewStockingIssues");var r=[];$.ajax({url:"/imad-rs/rest/card4",dataType:"json",success:function(o){r=o.results;if(r.length>0){var s=r[0]["outOfStockPercentage"];var c=r[0]["nearlyOutOfStockPercentage"];var n=r[0]["combinedPercentage"];a["sap.card"].header.title="Global Stocking Issues";a["sap.card"].content.body[1]["inlines"][0]["text"]=s+"%";a["sap.card"].content.body[3]["inlines"][0]["text"]=c+"%";a["sap.card"].content.body[6]["inlines"][0]["text"]=n+"%";e.setProperty("/overviewStockingIssues",a);t.refresh()}}})},populateCurrentStateOfStock:function(){var t=this.getView().byId("currentStateOfStock");var e=this.getView().getModel("cardModel");var a=e.getProperty("/currentStateOfStock");var r=[];$.ajax({url:"/imad-rs/rest/card5",dataType:"json",success:function(o){r=o.results;if(r.length>0){a["sap.card"].header.title="Products Out of Stock, or Nearly Out of Stock";a["sap.card"].header.subTitle=s.format(new Date);a["sap.card"].data.json.results=r;e.setProperty("/currentStateOfStock",a);t.refresh()}}})},populateCombinedPercentageHistory:function(){var t=this.getView().byId("combinedPercentageHistory");var e=this.getView().getModel("cardModel");var a=e.getProperty("/combinedPercentageHistory");var r=null;var o=[];$.when($.ajax({url:"/imad-rs/rest/card6",dataType:"json",success:function(t){r=t.results.header;o=t.results.list}})).then(function(){if(r!==null&&o.length>0){a["sap.card"].header.title="Historical Analysis of Global Combined Percentage";a["sap.card"].header.data.json=r;a["sap.card"].content.data.json.list=o;e.setProperty("/combinedPercentageHistory",a);t.refresh()}})}})});