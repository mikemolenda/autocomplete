  <div class="autocomplete-container">
<!-- 
  Uses JS doCompletion() to run "complete" action of AutoComplete servlet.
-->
    <form action="AutoComplete">
      <input 
        type="text" 
        class="form-control" 
        id="complete-field"
        onkeyup="doCompletion()">
    </form>

<!-- 
  Displays responses of autocomplete script.
  Responses contain hyperlinks to run "lookup" action of AutoComplete servlet.
-->
  <table class="table table-hover" id="complete-table"></table>

  </div>
