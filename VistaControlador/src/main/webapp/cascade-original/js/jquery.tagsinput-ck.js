/*

	jQuery Tags Input Plugin 1.3.3
	
	Copyright (c) 2011 XOXCO, Inc
	
	Documentation for this plugin lives here:
	http://xoxco.com/clickable/jquery-tags-input
	
	Licensed under the MIT license:
	http://www.opensource.org/licenses/mit-license.php

	ben@xoxco.com

*/(function(e) {
    var t = new Array, n = new Array;
    e.fn.doAutosize = function(t) {
        var n = e(this).data("minwidth"), r = e(this).data("maxwidth"), i = "", s = e(this), o = e("#" + e(this).data("tester_id"));
        if (i === (i = s.val())) return;
        var u = i.replace(/&/g, "&amp;").replace(/\s/g, " ").replace(/</g, "&lt;").replace(/>/g, "&gt;");
        o.html(u);
        var a = o.width(), f = a + t.comfortZone >= n ? a + t.comfortZone : n, l = s.width(), c = f < l && f >= n || f > n && f < r;
        c && s.width(f);
    };
    e.fn.resetAutosize = function(t) {
        var n = e(this).data("minwidth") || t.minInputWidth || e(this).width(), r = e(this).data("maxwidth") || t.maxInputWidth || e(this).closest(".tagsinput").width() - t.inputPadding, i = "", s = e(this), o = e("<tester/>").css({
            position: "absolute",
            top: -9999,
            left: -9999,
            width: "auto",
            fontSize: s.css("fontSize"),
            fontFamily: s.css("fontFamily"),
            fontWeight: s.css("fontWeight"),
            letterSpacing: s.css("letterSpacing"),
            whiteSpace: "nowrap"
        }), u = e(this).attr("id") + "_autosize_tester";
        if (!e("#" + u).length > 0) {
            o.attr("id", u);
            o.appendTo("body");
        }
        s.data("minwidth", n);
        s.data("maxwidth", r);
        s.data("tester_id", u);
        s.css("width", n);
    };
    e.fn.addTag = function(r, i) {
        i = jQuery.extend({
            focus: !1,
            callback: !0
        }, i);
        this.each(function() {
            var s = e(this).attr("id"), o = e(this).val().split(t[s]);
            o[0] == "" && (o = new Array);
            r = jQuery.trim(r);
            if (i.unique) {
                var u = e(this).tagExist(r);
                u == 1 && e("#" + s + "_tag").addClass("not_valid");
            } else var u = !1;
            if (r != "" && u != 1) {
                e("<span>").addClass("tag").append(e("<span>").text(r).append("&nbsp;&nbsp;"), e('<a class="tagsinput-remove-link">', {
                    href: "#",
                    title: "Remove tag",
                    text: ""
                }).click(function() {
                    return e("#" + s).removeTag(escape(r));
                })).insertBefore("#" + s + "_addTag");
                o.push(r);
                e("#" + s + "_tag").val("");
                i.focus ? e("#" + s + "_tag").focus() : e("#" + s + "_tag").blur();
                e.fn.tagsInput.updateTagsField(this, o);
                if (i.callback && n[s] && n[s].onAddTag) {
                    var a = n[s].onAddTag;
                    a.call(this, r);
                }
                if (n[s] && n[s].onChange) {
                    var f = o.length, a = n[s].onChange;
                    a.call(this, e(this), o[f - 1]);
                }
            }
        });
        return !1;
    };
    e.fn.removeTag = function(r) {
        r = unescape(r);
        this.each(function() {
            var s = e(this).attr("id"), o = e(this).val().split(t[s]);
            e("#" + s + "_tagsinput .tag").remove();
            str = "";
            for (i = 0; i < o.length; i++) o[i] != r && (str = str + t[s] + o[i]);
            e.fn.tagsInput.importTags(this, str);
            if (n[s] && n[s].onRemoveTag) {
                var u = n[s].onRemoveTag;
                u.call(this, r);
            }
        });
        return !1;
    };
    e.fn.tagExist = function(n) {
        var r = e(this).attr("id"), i = e(this).val().split(t[r]);
        return jQuery.inArray(n, i) >= 0;
    };
    e.fn.importTags = function(t) {
        id = e(this).attr("id");
        e("#" + id + "_tagsinput .tag").remove();
        e.fn.tagsInput.importTags(this, t);
    };
    e.fn.tagsInput = function(r) {
        var i = jQuery.extend({
            interactive: !0,
            defaultText: "",
            minChars: 0,
            width: "",
            height: "",
            autocomplete: {
                selectFirst: !1
            },
            hide: !0,
            delimiter: ",",
            unique: !0,
            removeWithBackspace: !0,
            placeholderColor: "#666666",
            autosize: !0,
            comfortZone: 20,
            inputPadding: 12
        }, r);
        this.each(function() {
            i.hide && e(this).hide();
            var r = e(this).attr("id");
            if (!r || t[e(this).attr("id")]) r = e(this).attr("id", "tags" + (new Date).getTime()).attr("id");
            var s = jQuery.extend({
                pid: r,
                real_input: "#" + r,
                holder: "#" + r + "_tagsinput",
                input_wrapper: "#" + r + "_addTag",
                fake_input: "#" + r + "_tag"
            }, i);
            t[r] = s.delimiter;
            if (i.onAddTag || i.onRemoveTag || i.onChange) {
                n[r] = new Array;
                n[r].onAddTag = i.onAddTag;
                n[r].onRemoveTag = i.onRemoveTag;
                n[r].onChange = i.onChange;
            }
            var o = e("#" + r).attr("class").replace("tagsinput", ""), u = '<div id="' + r + '_tagsinput" class="tagsinput ' + o + '"><div class="tagsinput-add-container" id="' + r + '_addTag"><div class="tagsinput-add"></div>';
            i.interactive && (u = u + '<input id="' + r + '_tag" value="" data-default="' + i.defaultText + '" />');
            u += "</div></div>";
            e(u).insertAfter(this);
            e(s.holder).css("width", i.width);
            e(s.holder).css("min-height", i.height);
            e(s.holder).css("height", "100%");
            e(s.real_input).val() != "" && e.fn.tagsInput.importTags(e(s.real_input), e(s.real_input).val());
            if (i.interactive) {
                e(s.fake_input).val(e(s.fake_input).attr("data-default"));
                e(s.fake_input).css("color", i.placeholderColor);
                e(s.fake_input).resetAutosize(i);
                e(s.holder).bind("click", s, function(t) {
                    e(t.data.fake_input).focus();
                });
                e(s.fake_input).bind("focus", s, function(t) {
                    e(t.data.fake_input).val() == e(t.data.fake_input).attr("data-default") && e(t.data.fake_input).val("");
                    e(t.data.fake_input).css("color", "#000000");
                });
                if (i.autocomplete_url != undefined) {
                    autocomplete_options = {
                        source: i.autocomplete_url
                    };
                    for (attrname in i.autocomplete) autocomplete_options[attrname] = i.autocomplete[attrname];
                    if (jQuery.Autocompleter !== undefined) {
                        e(s.fake_input).autocomplete(i.autocomplete_url, i.autocomplete);
                        e(s.fake_input).bind("result", s, function(t, n, s) {
                            n && e("#" + r).addTag(n[0] + "", {
                                focus: !0,
                                unique: i.unique
                            });
                        });
                    } else if (jQuery.ui.autocomplete !== undefined) {
                        e(s.fake_input).autocomplete(autocomplete_options);
                        e(s.fake_input).bind("autocompleteselect", s, function(t, n) {
                            e(t.data.real_input).addTag(n.item.value, {
                                focus: !0,
                                unique: i.unique
                            });
                            return !1;
                        });
                    }
                } else e(s.fake_input).bind("blur", s, function(t) {
                    var n = e(this).attr("data-default");
                    if (e(t.data.fake_input).val() != "" && e(t.data.fake_input).val() != n) t.data.minChars <= e(t.data.fake_input).val().length && (!t.data.maxChars || t.data.maxChars >= e(t.data.fake_input).val().length) && e(t.data.real_input).addTag(e(t.data.fake_input).val(), {
                        focus: !0,
                        unique: i.unique
                    }); else {
                        e(t.data.fake_input).val(e(t.data.fake_input).attr("data-default"));
                        e(t.data.fake_input).css("color", i.placeholderColor);
                    }
                    return !1;
                });
                e(s.fake_input).bind("keypress", s, function(t) {
                    if (t.which == t.data.delimiter.charCodeAt(0) || t.which == 13) {
                        t.preventDefault();
                        t.data.minChars <= e(t.data.fake_input).val().length && (!t.data.maxChars || t.data.maxChars >= e(t.data.fake_input).val().length) && e(t.data.real_input).addTag(e(t.data.fake_input).val(), {
                            focus: !0,
                            unique: i.unique
                        });
                        e(t.data.fake_input).resetAutosize(i);
                        return !1;
                    }
                    t.data.autosize && e(t.data.fake_input).doAutosize(i);
                });
                s.removeWithBackspace && e(s.fake_input).bind("keydown", function(t) {
                    if (t.keyCode == 8 && e(this).val() == "") {
                        t.preventDefault();
                        var n = e(this).closest(".tagsinput").find(".tag:last").text(), r = e(this).attr("id").replace(/_tag$/, "");
                        n = n.replace(/[\s\u00a0]+x$/, "");
                        e("#" + r).removeTag(escape(n));
                        e(this).trigger("focus");
                    }
                });
                e(s.fake_input).blur();
                s.unique && e(s.fake_input).keydown(function(t) {
                    (t.keyCode == 8 || String.fromCharCode(t.which).match(/\w+|[????????????????????????,/]+/)) && e(this).removeClass("not_valid");
                });
            }
        });
        return this;
    };
    e.fn.tagsInput.updateTagsField = function(n, r) {
        var i = e(n).attr("id");
        e(n).val(r.join(t[i]));
    };
    e.fn.tagsInput.importTags = function(r, s) {
        e(r).val("");
        var o = e(r).attr("id"), u = s.split(t[o]);
        for (i = 0; i < u.length; i++) e(r).addTag(u[i], {
            focus: !1,
            callback: !1
        });
        if (n[o] && n[o].onChange) {
            var a = n[o].onChange;
            a.call(r, r, u[i]);
        }
    };
})(jQuery);