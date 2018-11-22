function upvote(post_id)
{
    let vote = new Vote(true, loginUsername, userPassword);
    send_vote_to_server(vote, post_id);
}


function downvote(post_id)
{
    let vote = new Vote(false, loginUsername, userPassword);
    send_vote_to_server(vote, post_id);
}


function send_vote_to_server(vote, post_id)
{
    $.ajax({
        dataType: "json",
        type: "post",
        method: "PUT",
        url: "http://localhost:4567/rest/post/" + post_id,
        data: JSON.stringify(vote),
        success: on_success,
        error: function(jqXHR, textStatus, errorThrown){
            console.log("ERROR sending the vote to server!")
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function on_success(response)
{
    if(response.post_id!=null)
    {
        let voteText = $("#votes_for_post_" + response.post_id)[0];
        voteText.innerHTML = response.votes;
    }else{
        console.log(response);
    }
}



class Vote
{
    constructor(up, username, password)
    {
        this.up = up;
        this.username = username;
        this.password = password;
    }
}