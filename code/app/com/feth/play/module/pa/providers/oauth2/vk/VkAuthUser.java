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
name|oauth2
operator|.
name|vk
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
name|oauth2
operator|.
name|BasicOAuth2AuthUser
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

begin_comment
comment|/**  * @author Denis Borisenko  */
end_comment

begin_class
specifier|public
class|class
name|VkAuthUser
extends|extends
name|BasicOAuth2AuthUser
implements|implements
name|ExtendedIdentity
implements|,
name|PicturedIdentity
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
specifier|abstract
class|class
name|Constants
block|{
specifier|public
specifier|static
specifier|final
name|String
name|UID
init|=
literal|"uid"
decl_stmt|;
comment|// 2037793
specifier|public
specifier|static
specifier|final
name|String
name|FIRST_NAME
init|=
literal|"first_name"
decl_stmt|;
comment|// "ÐÐµÐ½Ð¸Ñ"
specifier|public
specifier|static
specifier|final
name|String
name|LAST_NAME
init|=
literal|"last_name"
decl_stmt|;
comment|// "ÐÐ¾ÑÐ¸ÑÐµÐ½ÐºÐ¾"
specifier|public
specifier|static
specifier|final
name|String
name|NICKNAME
init|=
literal|"nickname"
decl_stmt|;
comment|// ''
specifier|public
specifier|static
specifier|final
name|String
name|SCREEN_NAME
init|=
literal|"screen_name"
decl_stmt|;
comment|// 'dborisenko'
specifier|public
specifier|static
specifier|final
name|String
name|GENDER
init|=
literal|"sex"
decl_stmt|;
comment|// 2
specifier|public
specifier|static
specifier|final
name|String
name|BIRTH_DATE
init|=
literal|"bdate"
decl_stmt|;
comment|// '9.2.1986'
specifier|public
specifier|static
specifier|final
name|String
name|TIMEZONE
init|=
literal|"timezone"
decl_stmt|;
comment|// 1
specifier|public
specifier|static
specifier|final
name|String
name|PHOTO_50
init|=
literal|"photo_50"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PHOTO_100
init|=
literal|"photo_100"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PHOTO_200
init|=
literal|"photo_200_orig"
decl_stmt|;
block|}
specifier|private
name|String
name|firstName
decl_stmt|;
specifier|private
name|String
name|lastName
decl_stmt|;
specifier|private
name|String
name|nickname
decl_stmt|;
specifier|private
name|String
name|screenName
decl_stmt|;
specifier|private
name|String
name|gender
decl_stmt|;
specifier|private
name|String
name|birthDate
decl_stmt|;
specifier|private
name|int
name|timezone
decl_stmt|;
specifier|private
name|String
name|photo50
decl_stmt|;
specifier|private
name|String
name|photo100
decl_stmt|;
specifier|private
name|String
name|photo200
decl_stmt|;
specifier|public
name|VkAuthUser
parameter_list|(
specifier|final
name|JsonNode
name|node
parameter_list|,
specifier|final
name|VkAuthInfo
name|info
parameter_list|,
specifier|final
name|String
name|state
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
name|UID
argument_list|)
operator|.
name|asText
argument_list|()
argument_list|,
name|info
argument_list|,
name|state
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
name|FIRST_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|firstName
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|FIRST_NAME
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
name|LAST_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|lastName
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|LAST_NAME
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
name|NICKNAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|nickname
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|NICKNAME
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
name|GENDER
argument_list|)
condition|)
block|{
name|int
name|genderId
init|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|GENDER
argument_list|)
operator|.
name|asInt
argument_list|()
decl_stmt|;
if|if
condition|(
name|genderId
operator|==
literal|1
condition|)
name|this
operator|.
name|gender
operator|=
literal|"female"
expr_stmt|;
elseif|else
if|if
condition|(
name|genderId
operator|==
literal|2
condition|)
name|this
operator|.
name|gender
operator|=
literal|"male"
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
name|BIRTH_DATE
argument_list|)
condition|)
block|{
name|this
operator|.
name|birthDate
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|BIRTH_DATE
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
name|TIMEZONE
argument_list|)
condition|)
block|{
name|this
operator|.
name|timezone
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|TIMEZONE
argument_list|)
operator|.
name|asInt
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
name|PHOTO_50
argument_list|)
condition|)
block|{
name|this
operator|.
name|photo50
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|PHOTO_50
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
name|PHOTO_100
argument_list|)
condition|)
block|{
name|this
operator|.
name|photo100
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|PHOTO_100
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
name|PHOTO_200
argument_list|)
condition|)
block|{
name|this
operator|.
name|photo200
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|PHOTO_200
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
literal|null
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
name|VkAuthProvider
operator|.
name|PROVIDER_KEY
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
name|getFirstName
argument_list|()
operator|+
literal|" "
operator|+
name|getLastName
argument_list|()
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
name|getPicture
parameter_list|()
block|{
return|return
name|getPhoto200
argument_list|()
return|;
block|}
specifier|public
name|String
name|getBirthDate
parameter_list|()
block|{
return|return
name|birthDate
return|;
block|}
specifier|public
name|String
name|getNickname
parameter_list|()
block|{
return|return
name|nickname
return|;
block|}
specifier|public
name|String
name|getPhoto100
parameter_list|()
block|{
return|return
name|photo100
return|;
block|}
specifier|public
name|String
name|getPhoto200
parameter_list|()
block|{
return|return
name|photo200
return|;
block|}
specifier|public
name|String
name|getPhoto50
parameter_list|()
block|{
return|return
name|photo50
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
name|int
name|getTimezone
parameter_list|()
block|{
return|return
name|timezone
return|;
block|}
block|}
end_class

end_unit

