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
name|user
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

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
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|LocaleUtils
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|AuthUser
implements|implements
name|AuthUserIdentity
implements|,
name|Serializable
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
specifier|public
specifier|static
specifier|final
name|long
name|NO_EXPIRATION
init|=
operator|-
literal|1L
decl_stmt|;
specifier|public
name|long
name|expires
parameter_list|()
block|{
return|return
name|NO_EXPIRATION
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
specifier|final
name|int
name|prime
init|=
literal|31
decl_stmt|;
name|int
name|result
init|=
literal|1
decl_stmt|;
name|result
operator|=
name|prime
operator|*
name|result
operator|+
operator|(
operator|(
name|getId
argument_list|()
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|getId
argument_list|()
operator|.
name|hashCode
argument_list|()
operator|)
expr_stmt|;
name|result
operator|=
name|prime
operator|*
name|result
operator|+
operator|(
operator|(
name|getProvider
argument_list|()
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|getProvider
argument_list|()
operator|.
name|hashCode
argument_list|()
operator|)
expr_stmt|;
return|return
name|result
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|obj
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|obj
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getClass
argument_list|()
operator|!=
name|obj
operator|.
name|getClass
argument_list|()
condition|)
return|return
literal|false
return|;
specifier|final
name|AuthUserIdentity
name|other
init|=
operator|(
name|AuthUserIdentity
operator|)
name|obj
decl_stmt|;
if|if
condition|(
name|getId
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|getId
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
block|}
elseif|else
if|if
condition|(
operator|!
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|other
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getProvider
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|getProvider
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
block|}
elseif|else
if|if
condition|(
operator|!
name|getProvider
argument_list|()
operator|.
name|equals
argument_list|(
name|other
operator|.
name|getProvider
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getId
argument_list|()
operator|+
literal|"@"
operator|+
name|getProvider
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|Locale
name|getLocaleFromString
parameter_list|(
specifier|final
name|String
name|locale
parameter_list|)
block|{
if|if
condition|(
name|locale
operator|!=
literal|null
operator|&&
operator|!
name|locale
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
return|return
name|LocaleUtils
operator|.
name|toLocale
argument_list|(
name|locale
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|java
operator|.
name|lang
operator|.
name|IllegalArgumentException
name|iae
parameter_list|)
block|{
try|try
block|{
return|return
name|LocaleUtils
operator|.
name|toLocale
argument_list|(
name|locale
operator|.
name|replace
argument_list|(
literal|'-'
argument_list|,
literal|'_'
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|java
operator|.
name|lang
operator|.
name|IllegalArgumentException
name|iae2
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
parameter_list|<
name|T
extends|extends
name|AuthUserIdentity
operator|&
name|NameIdentity
parameter_list|>
name|String
name|toString
parameter_list|(
specifier|final
name|T
name|identity
parameter_list|)
block|{
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
name|identity
operator|.
name|getName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|identity
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|identity
operator|instanceof
name|EmailIdentity
condition|)
block|{
specifier|final
name|EmailIdentity
name|i2
init|=
operator|(
name|EmailIdentity
operator|)
name|identity
decl_stmt|;
if|if
condition|(
name|i2
operator|.
name|getEmail
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"("
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|i2
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|") "
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|identity
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|" @ "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|identity
operator|.
name|getProvider
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

