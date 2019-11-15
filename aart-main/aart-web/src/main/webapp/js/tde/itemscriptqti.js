var itemQti = {
	    // The id used during communications with the global context. The id will be set
	    // during the initialize method below.
	    id : -1,
	    // An object used by the custom interaction.
	    graph : null,

	     // The getResponse method returns an object aligned with the types defined in the
	     // appendix of this document.
	     getResponse : function() {
	       return {"base":{"point":[3, 4]}};
	     },

	     // This is an optional method for a custom interaction to save a serialized state
	     // in order for the interaction to return to a prior state.
	     getSerializedState : function() {
	       // Nothing is serialized for this interaction.
	       return $('#'+this.id).get(0);
	     },

	     // The type identifier allows custom interactions types to be identified in an item
	     // Returned values are defined by the implementer.
	     getTypeIdentifier : function() {
	       return 'jq-item-container';//'IW30MX6U48JF9120GJS';
	     },

	     // The initialize method is called from the global context to initialize the custom
	     // interaction instance.
	     initialize : function(id, htmlNode, state, response) {
	       // The id must be retained for future communication with the global context.
	       this.id = id;
	       // The following object is specific to this instance of the custom interaction.
	      // this.graph = JXG.JSXGraph.initBoard(config.div, config.setup);
	       // Custom interaction configuration data may be accessed from the global context.
	       var config = qtiCustomInteractionContext.getConfiguration(this.id);
	       // This method is specific to the custom interaction implementation.
	      /* if (config.draw) {
	         this.graph.suspendUpdate();
	         config.draw(this.graph);
	         this.graph.unsuspendUpdate();
	       }*/
	       // The global context notifyReady method must be called when the custom
	       // interaction initialization is complete.
	       qtiCustomInteractionContext.notifyReady(this.id);
	     },

	     // This method is an implementation specific method informing the
	     // global context object.
	     interactionFinished : function() {
	       // The global context is informed when the interaction is finished.
	       qtiCustomInteractionContext.notifyDone(this.id);
	     }
};