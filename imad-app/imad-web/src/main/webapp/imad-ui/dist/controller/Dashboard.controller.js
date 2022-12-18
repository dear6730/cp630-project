sap.ui.define(["sap/ui/core/mvc/Controller","sap/ui/core/format/NumberFormat","sap/ui/core/format/DateFormat"],function(t,e,a){"use strict";var r={style:"short",decimals:1,shortDecimals:2};var o=e.getFloatInstance(r);var s=a.getDateInstance({pattern:"MMM d, y"});return t.extend("ec.laurier.imad.controller.Dashboard",{onInit:function(){this.populateTotalStockValue();this.populateTotalStockValueByCategory();this.populateTop5ProductsSold();this.populateOverviewStockingIssues();this.populateCurrentStateOfStock();this.populateProductsOutOfStockOrNearlyOutOfStock()},populateTotalStockValue:function(){var t=this.getView().byId("totalStockValue");var e=this.getView().getModel("cardModel");var a=e.getProperty("/totalStockValue");var r;var c=[];var u=[];$.when($.ajax({url:"/imad-rs/rest/card1",dataType:"json",success:function(t){r=t.results.header;c=t.results.list;u=t.results.measures}})).then(function(){if(r!==null&&c.length>0&&u.length>0){r.n="$"+o.format(r.n);a["sap.card"].header.title="Total Stock value";a["sap.card"].header.details="as of "+s.format(new Date(r.details));a["sap.card"].header.data.json=r;a["sap.card"].content.data.json.list=c;a["sap.card"].content.measures=u;e.setProperty("/totalStockValue",a);t.refresh()}})},populateTotalStockValueByCategory:function(){var t=this.getView().byId("totalStockValueByCategory");var e=this.getView().getModel("cardModel");var a=e.getProperty("/totalStockValueByCategory");var r=[];$.ajax({url:"/imad-rs/rest/card2",dataType:"json",success:function(o){r=o.results;if(r.length>0){a["sap.card"].header.title="Total Stock by Category";a["sap.card"].content.data.json.measures=r;e.setProperty("/totalStockValueByCategory",a);t.refresh()}}})},populateTop5ProductsSold:function(){var t=this.getView().byId("top5ProductsSold");var e=this.getView().getModel("cardModel");var a=e.getProperty("/top5ProductsSold");var r=[];$.ajax({url:"/imad-rs/rest/card3",dataType:"json",success:function(o){r=o.results;if(r.length>0){a["sap.card"].header.title="Stock Value of Top 5 Products";a["sap.card"].content.data.json.list=r;e.setProperty("/top5ProductsSold",a);t.refresh()}}})},populateOverviewStockingIssues:function(){var t=this.getView().byId("overviewStockingIssues");var e=this.getView().getModel("cardModel");var a=e.getProperty("/overviewStockingIssues");var r=[];$.ajax({url:"/imad-rs/rest/card4",dataType:"json",success:function(o){r=o.results;if(r.length>0){var s=r[0]["percentageOutOfStock"];var c=r[0]["percentageNearlyOutOfStock"];a["sap.card"].header.title="Overview of Stocking Issues";a["sap.card"].content.body[1]["inlines"][0]["text"]=s+"%";a["sap.card"].content.body[4]["inlines"][0]["text"]=c+"%";e.setProperty("/overviewStockingIssues",a);t.refresh()}}})},populateCurrentStateOfStock:function(){var t=this.getView().byId("currentStateOfStock");var e=this.getView().getModel("cardModel");var a=e.getProperty("/currentStateOfStock");var r=[];$.ajax({url:"/imad-rs/rest/card5",dataType:"json",success:function(o){r=o.results;if(r.length>0){a["sap.card"].header.title="Current State of Stock";a["sap.card"].header.subTitle=s.format(new Date);a["sap.card"].data.json.results=r;e.setProperty("/currentStateOfStock",a);t.refresh()}}})},populateProductsOutOfStockOrNearlyOutOfStock:function(){var t=this.getView().byId("productsOutOfStockOrNearlyOutOfStock");var e=this.getView().getModel("cardModel");var a=e.getProperty("/productsOutOfStockOrNearlyOutOfStock");var r=[];var o=[];$.when($.ajax({url:"/imad-rs/rest/card6Header",dataType:"json",success:function(t){r=t.results}}),$.ajax({url:"/imad-rs/rest/card6",dataType:"json",success:function(t){o=t.results}})).then(function(){if(r.length>0&&o.length>0){a["sap.card"].header.title="Products Out of Stock, or Nearly Out of Stock";a["sap.card"].header.data.json=r[0];a["sap.card"].content.data.json.list=o;e.setProperty("/productsOutOfStockOrNearlyOutOfStock",a);t.refresh()}})}})});