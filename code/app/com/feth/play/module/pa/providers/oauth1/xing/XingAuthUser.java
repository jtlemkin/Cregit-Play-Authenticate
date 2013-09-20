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
name|xing
package|;
end_package

begin_import
import|import
name|com
operator|.
name|fasterxml
operator|.
name|jackson
operator|.
name|databind
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
name|ExtendedIdentity
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
name|ProfiledIdentity
import|;
end_import

begin_comment
comment|/**  * See https://dev.xing.com/docs/get/users/me  */
end_comment

begin_class
specifier|public
class|class
name|XingAuthUser
extends|extends
name|BasicOAuth1AuthUser
implements|implements
name|ExtendedIdentity
implements|,
name|PicturedIdentity
implements|,
name|ProfiledIdentity
block|{
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
specifier|final
name|String
name|ACTIVE_EMAIL
init|=
literal|"active_email"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|DISPLAY_NAME
init|=
literal|"display_name"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|FIRST_NAME
init|=
literal|"first_name"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|GENDER
init|=
literal|"gender"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"id"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|LAST_NAME
init|=
literal|"last_name"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|PERMALINK
init|=
literal|"permalink"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|PHOTO_URLS
init|=
literal|"photo_urls"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|PHOTO_URLS_LARGE
init|=
literal|"large"
decl_stmt|;
specifier|private
name|String
name|displayName
decl_stmt|;
specifier|private
name|String
name|email
decl_stmt|;
specifier|private
name|String
name|firstName
decl_stmt|;
specifier|private
name|String
name|gender
decl_stmt|;
specifier|private
name|String
name|largePhotoUrl
decl_stmt|;
specifier|private
name|String
name|lastName
decl_stmt|;
specifier|private
name|String
name|permalink
decl_stmt|;
specifier|public
name|XingAuthUser
parameter_list|(
specifier|final
name|JsonNode
name|nodeInfo
parameter_list|,
specifier|final
name|OAuth1AuthInfo
name|info
parameter_list|)
block|{
comment|// 'state' is always null?
name|super
argument_list|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|ID
argument_list|)
condition|?
name|nodeInfo
operator|.
name|get
argument_list|(
name|ID
argument_list|)
operator|.
name|asText
argument_list|()
else|:
literal|"N/A"
argument_list|,
name|info
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|DISPLAY_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|displayName
operator|=
name|nodeInfo
operator|.
name|get
argument_list|(
name|DISPLAY_NAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|ACTIVE_EMAIL
argument_list|)
condition|)
block|{
name|this
operator|.
name|email
operator|=
name|nodeInfo
operator|.
name|get
argument_list|(
name|ACTIVE_EMAIL
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|GENDER
argument_list|)
condition|)
block|{
name|this
operator|.
name|gender
operator|=
name|nodeInfo
operator|.
name|get
argument_list|(
name|GENDER
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|FIRST_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|firstName
operator|=
name|nodeInfo
operator|.
name|get
argument_list|(
name|FIRST_NAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|LAST_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|lastName
operator|=
name|nodeInfo
operator|.
name|get
argument_list|(
name|LAST_NAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|PHOTO_URLS
argument_list|)
condition|)
block|{
specifier|final
name|JsonNode
name|photoUrlsNode
init|=
name|nodeInfo
operator|.
name|get
argument_list|(
name|PHOTO_URLS
argument_list|)
decl_stmt|;
if|if
condition|(
name|photoUrlsNode
operator|.
name|has
argument_list|(
name|PHOTO_URLS_LARGE
argument_list|)
condition|)
block|{
name|this
operator|.
name|largePhotoUrl
operator|=
name|photoUrlsNode
operator|.
name|get
argument_list|(
name|PHOTO_URLS_LARGE
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|PERMALINK
argument_list|)
condition|)
block|{
name|this
operator|.
name|permalink
operator|=
name|nodeInfo
operator|.
name|get
argument_list|(
name|PERMALINK
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
name|getEmail
parameter_list|()
block|{
return|return
name|email
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getFirstName
parameter_list|()
block|{
return|return
name|firstName
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getGender
parameter_list|()
block|{
return|return
name|gender
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getLastName
parameter_list|()
block|{
return|return
name|lastName
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|displayName
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPicture
parameter_list|()
block|{
return|return
name|largePhotoUrl
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getProfileLink
parameter_list|()
block|{
return|return
name|permalink
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getProvider
parameter_list|()
block|{
return|return
name|XingAuthProvider
operator|.
name|PROVIDER_KEY
return|;
block|}
block|}
end_class

end_unit

