const VISIBLE_CLASS = "form-group";
const HIDDEN_CLASS = "hidden";

function link_post_mode()
{
    $("#contentinputwrapper").attr("class", HIDDEN_CLASS);
    $("#linkinputwrapper").attr("class", VISIBLE_CLASS);
}

function self_post_mode()
{
    $("#linkinputwrapper").attr("class", HIDDEN_CLASS);
    $("#contentinputwrapper").attr("class", VISIBLE_CLASS);
}