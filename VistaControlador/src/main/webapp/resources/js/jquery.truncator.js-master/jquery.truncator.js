// HTML Truncator for jQuery
// by Henrik Nyh <http://henrik.nyh.se> 2008-02-28.
// Free to modify and redistribute with credit.

(function($) {

  var trailing_whitespace = true;
  var truncateExecuted = false;

  $.fn.truncate = function(options) {

    var opts = $.extend({}, $.fn.truncate.defaults, options);
    
    if (opts.overTitle) {
        truncateExecuted = false;
//        console.log('Se va a procesar por titulo...');
        var titleText = $(this).attr('title');
//        console.log('El titulo es ' + titleText);
        if (titleText != null) {
            if (!truncateExecuted && titleText.length > opts.max_length) {
                truncateExecuted = true;
                var titleTextTemp = titleText.slice(0, opts.max_length);
//                console.log('Luego del slice el titulo es ' + titleTextTemp);
                titleText = titleTextTemp + opts.more;
            }
//            console.log('El titulo despues de procesado es ' + titleText);
            $(this).attr('title', titleText);
        }
    }
    
    $(this).each(function() {

        var content_length = $.trim(squeeze($(this).text())).length;
        if (content_length <= opts.max_length)
          return;  // bail early if not overlong

        // include more text, link prefix, and link suffix in max length
  //      var actual_max_length = opts.max_length - opts.more.length - opts.link_prefix.length - opts.link_suffix.length;
        // include more text in max length
        var actual_max_length = opts.max_length;
        var moreSuffix = opts.more;
        truncateExecuted = false;
//        console.log('actual_max_length es: ' + actual_max_length);
        var truncated_node = recursivelyTruncate(this, actual_max_length, moreSuffix);

          var full_node = $(this).hide();
          truncated_node.insertAfter(full_node);
          $(this).detach();
          //truncated_node.append(opts.more);

  //      findNodeForMore(truncated_node).append(opts.more);
        //findNodeForMore(truncated_node).append(opts.link_prefix+'<a href="#more" class="'+opts.css_more_class+'">'+opts.more+'</a>'+opts.link_suffix);
        //findNodeForLess(full_node).append(opts.link_prefix+'<a href="#less" class="'+opts.css_less_class+'">'+opts.less+'</a>'+opts.link_suffix);

        /*truncated_node.find('a:last').click(function() {
          truncated_node.hide(); full_node.show(); return false;
        });
        full_node.find('a:last').click(function() {
          truncated_node.show(); full_node.hide(); return false;
        });*/
        
    });
    
  }
  
  String.prototype.ltrim = function() {
    return this.replace(/^\s+/,"");
  }

  // Note that the " (…more)" bit counts towards the max length – so a max
  // length of 10 would truncate "1234567890" to "12 (…more)".
  $.fn.truncate.defaults = {
    max_length: 100,
    more: '…more',
    less: 'less',
    css_more_class: 'truncator-link truncator-more',
    css_less_class: 'truncator-link truncator-less',
    link_prefix: ' (',
    link_suffix: ')',
    overTitle: false
  };

  function recursivelyTruncate(node, max_length, more) {
      
//      if (node.nodeType == 3) 
//          console.log('es nodo tipo texto: ' + node.toString());
//      else 
//          console.log('es nodo no es tipo texto: ' + node.toString() + " pero es de tipo " + node.nodeType);
//    return (node.nodeType == 3) ? truncateText(node, max_length) : truncateNode(node, max_length);
    return (node.nodeType == 3) ? truncateText(node, max_length, more) : truncateNode(node, max_length, more);
  }

  function truncateNode(node, max_length, more) {
    var node = $(node);
    var new_node = node.clone().empty();
    var truncatedChild;
    node.contents().each(function() {
      var remaining_length = max_length - new_node.text().ltrim().length;
//      if (remaining_length == 0) return;  // breaks the loop
      truncatedChild = recursivelyTruncate(this, remaining_length, more);
      if (truncatedChild) new_node.append(truncatedChild);
    });
    return new_node;
  }

  function truncateText(node, max_length, more) {
    var text = squeeze(node.data);
    var overflow = false;
    if (trailing_whitespace)  // remove initial whitespace if last text
      text = text.replace(/^ /, '');  // node had trailing whitespace.
    trailing_whitespace = !!text.match(/ $/);
    if (!truncateExecuted && text.ltrim().length > max_length) {overflow = true; truncateExecuted = true;}
    var initialIndex = 0;
    var textNoLeftSpace = '';
//    console.log('procesando el texto $' + text + '$');
    if (text.indexOf(' ') == 0) {
//        console.log('El nodo tiene espacio a la izquierda y el texto inicia en el indice ' + text.indexOf(' '))
//        console.log('texto antes de ltrim es $' + text + '$');
        textNoLeftSpace = text.ltrim(); 
//        console.log('texto despues de ltrim es $' + textNoLeftSpace + '$');
        initialIndex = text.length - textNoLeftSpace.length;
//        console.log('initialIndex antes de ajuste es: ' + initialIndex);
    }
//    console.log('initialIndex es: ' + initialIndex + ' hasta ' + (max_length + initialIndex) + 'caracteres');
    var text = text.slice(0, (max_length + initialIndex));
//    console.log('texto procesado $' + text + '$');
      if (overflow) text += more;
    // Ensure HTML entities are encoded
    // http://debuggable.com/posts/encode-html-entities-with-jquery:480f4dd6-13cc-4ce9-8071-4710cbdd56cb
    text = $('<div/>').text(text).html();
    return text;
  }

  // Collapses a sequence of whitespace into a single space.
  function squeeze(string) {
    return string.replace(/\s+/g, ' ');
  }

  // Finds the last, innermost block-level element
  function findNodeForMore(node) {
    var $node = $(node);
    var last_child = $node.children(":last");
    if (!last_child) return node;
    var display = last_child.css('display');
    if (!display || display=='inline') return $node;
    return findNodeForMore(last_child);
  };

  // Finds the last child if it's a p; otherwise the parent
  function findNodeForLess(node) {
    var $node = $(node);
    var last_child = $node.children(":last");
    if (last_child && last_child.is('p')) return last_child;
    return node;
  };

})(jQuery);
