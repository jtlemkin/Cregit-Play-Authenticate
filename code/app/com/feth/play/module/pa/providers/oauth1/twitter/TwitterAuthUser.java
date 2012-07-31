begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|providers
operator|.
name|oauth1
operator|.
name|twitter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|JsonNode
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|providers
operator|.
name|oauth1
operator|.
name|BasicOAuth1AuthUser
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|providers
operator|.
name|oauth1
operator|.
name|OAuth1AuthInfo
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|user
operator|.
name|AuthUser
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|user
operator|.
name|LocaleIdentity
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|user
operator|.
name|PicturedIdentity
import|;
end_import

begin_class
specifier|public
class|class
name|TwitterAuthUser
extends|extends
name|BasicOAuth1AuthUser
implements|implements
name|PicturedIdentity
implements|,
name|LocaleIdentity
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
specifier|static
specifier|abstract
class|class
name|Constants
block|{
comment|// {
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"id"
decl_stmt|;
comment|// "id":15484335,
comment|// "listed_count":5,
specifier|public
specifier|static
specifier|final
name|String
name|PROFILE_IMAGE_URL
init|=
literal|"profile_image_url"
decl_stmt|;
comment|// "profile_image_url":"http://a0.twimg.com/profile_images/57096786/j_48x48_normal.png",
comment|// "following":false,
comment|// "followers_count":118,
comment|// "location":"Sydney, Australia",
comment|// "contributors_enabled":false,
comment|// "profile_background_color":"C0DEED",
comment|// "time_zone":"Berlin",
comment|// "geo_enabled":true,
comment|// "utc_offset":3600,
comment|// "is_translator":false,
specifier|public
specifier|static
specifier|final
name|String
name|NAME
init|=
literal|"name"
decl_stmt|;
comment|// "name":"Joscha Feth",
comment|// "profile_background_image_url":"http://a0.twimg.com/images/themes/theme1/bg.png",
comment|// "show_all_inline_media":false,
specifier|public
specifier|static
specifier|final
name|String
name|SCREEN_NAME
init|=
literal|"screen_name"
decl_stmt|;
comment|// "screen_name":"joschafeth",
comment|// "protected":false,
comment|// "profile_link_color":"0084B4",
comment|// "default_profile_image":false,
comment|// "follow_request_sent":false,
comment|// "profile_background_image_url_https":"https://si0.twimg.com/images/themes/theme1/bg.png",
comment|// "favourites_count":3,
comment|// "notifications":false,
specifier|public
specifier|static
specifier|final
name|String
name|VERIFIED
init|=
literal|"verified"
decl_stmt|;
comment|// "verified":false,
comment|// "profile_use_background_image":true,
comment|// "profile_text_color":"333333",
comment|// "description":"",
comment|// "id_str":"15484335",
specifier|public
specifier|static
specifier|final
name|String
name|LOCALE
init|=
literal|"lang"
decl_stmt|;
comment|// "lang":"en",
comment|// "profile_sidebar_border_color":"C0DEED",
comment|// "profile_image_url_https":"https://si0.twimg.com/profile_images/57096786/j_48x48_normal.png",
comment|// "default_profile":true,
comment|// "url":null,
comment|// "statuses_count":378,
comment|// "status":{
comment|// "in_reply_to_user_id":11111,
comment|// "truncated":false,
comment|// "created_at":"Mon Jul 23 13:22:31 +0000 2012",
comment|// "coordinates":null,
comment|// "geo":null,
comment|// "favorited":false,
comment|// "in_reply_to_screen_name":"XXX",
comment|// "contributors":null,
comment|// "in_reply_to_status_id_str":"111111",
comment|// "place":null,
comment|// "source":"<a href=\"http://itunes.apple.com/us/app/twitter/id409789998?mt=12\" rel=\"nofollow\">Twitter for Mac</a>",
comment|// "in_reply_to_user_id_str":"11111",
comment|// "id":111111,
comment|// "id_str":"111111",
comment|// "retweeted":false,
comment|// "retweet_count":0,
comment|// "in_reply_to_status_id":11111,
comment|// "text":"some text to up to 140chars here"
comment|// },
comment|// "profile_background_tile":false,
comment|// "friends_count":120,
comment|// "created_at":"Fri Jul 18 18:17:46 +0000 2008",
comment|// "profile_sidebar_fill_color":"DDEEF6"
block|}
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|String
name|screenName
decl_stmt|;
specifier|private
name|boolean
name|verified
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|locale
decl_stmt|;
specifier|private
name|String
name|picture
decl_stmt|;
specifier|public
name|TwitterAuthUser
parameter_list|(
specifier|final
name|JsonNode
name|node
parameter_list|,
specifier|final
name|OAuth1AuthInfo
name|info
parameter_list|)
block|{
name|super
argument_list|(
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|ID
argument_list|)
operator|.
name|asText
argument_list|()
argument_list|,
name|info
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|name
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|NAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|LOCALE
argument_list|)
condition|)
block|{
name|this
operator|.
name|locale
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|LOCALE
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|SCREEN_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|screenName
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|SCREEN_NAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|VERIFIED
argument_list|)
condition|)
block|{
name|this
operator|.
name|verified
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|VERIFIED
argument_list|)
operator|.
name|asBoolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|PROFILE_IMAGE_URL
argument_list|)
condition|)
block|{
name|this
operator|.
name|picture
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|PROFILE_IMAGE_URL
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getProvider
parameter_list|()
block|{
return|return
name|TwitterAuthProvider
operator|.
name|PROVIDER_KEY
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
specifier|public
name|String
name|getScreenName
parameter_list|()
block|{
return|return
name|screenName
return|;
block|}
specifier|public
name|boolean
name|isVerified
parameter_list|()
block|{
return|return
name|verified
return|;
block|}
specifier|public
name|String
name|getPicture
parameter_list|()
block|{
return|return
name|picture
return|;
block|}
specifier|public
name|Locale
name|getLocale
parameter_list|()
block|{
return|return
name|AuthUser
operator|.
name|getLocaleFromString
argument_list|(
name|locale
argument_list|)
return|;
block|}
block|}
end_class

end_unit

