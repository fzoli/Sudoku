(function() {

	$(document).ready(function() {

		jQuery(function($) {
			$.mask.definitions["N"] = "[123456789]";
			$("input.numbers_empty, input.numbers_good, input.numbers_bad").mask("N", {placeholder: ""});
		});

                $("input.numbers_generated, input.numbers_solved").attr("readonly", "readonly");
                
                $("input.disabled, input.confirm_disabled").attr("disabled", "disabled");

                $("input.numbers_good, input.numbers_bad").keydown(function() {
                    $(this).attr('class','numbers_empty');
                });

                $("input.confirm_button").click(function() {
                    return confirm("Biztos benne?");
                });

                var notUnique = $("table#sudoku").attr("class") == "false";

                if ($.cookie('sudoku_warning') == null && notUnique) {
                    $.cookie('sudoku_warning', 'true');
                    alert("Tisztelt felhasználó!\n\nNe haragudjon, hogy zavarom, de szeretném tájékoztatni, hogy több megoldás is lehetséges a megadott rácshoz.\nAz egyik lehetséges megoldással kitöltöttem a mezőket.\n\nÍgérem, többet nem fogom zavarni magát ezzel az üzenettel, csak ha nyomós okom van rá.\n\nÜdvözlettel: Javascript programnyelv");
                }
                if (!notUnique) $.cookie('sudoku_warning', null);
                
	});

})();