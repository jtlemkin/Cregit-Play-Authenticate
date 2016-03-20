begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|models
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|persistence
operator|.
name|Column
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|persistence
operator|.
name|Entity
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|persistence
operator|.
name|Id
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|persistence
operator|.
name|ManyToOne
import|;
end_import

begin_import
import|import
name|play
operator|.
name|data
operator|.
name|format
operator|.
name|Formats
import|;
end_import

begin_import
import|import
name|com
operator|.
name|avaje
operator|.
name|ebean
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|com
operator|.
name|avaje
operator|.
name|ebean
operator|.
name|Ebean
import|;
end_import

begin_import
import|import
name|com
operator|.
name|avaje
operator|.
name|ebean
operator|.
name|QueryIterator
import|;
end_import

begin_import
import|import
name|com
operator|.
name|avaje
operator|.
name|ebean
operator|.
name|annotation
operator|.
name|EnumValue
import|;
end_import

begin_class
annotation|@
name|Entity
specifier|public
class|class
name|TokenAction
extends|extends
name|Model
block|{
specifier|public
enum|enum
name|Type
block|{
annotation|@
name|EnumValue
argument_list|(
literal|"EV"
argument_list|)
name|EMAIL_VERIFICATION
block|,
annotation|@
name|EnumValue
argument_list|(
literal|"PR"
argument_list|)
name|PASSWORD_RESET
block|}
comment|/** 	 * 	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/** 	 * Verification time frame (until the user clicks on the link in the email) 	 * in seconds 	 * Defaults to one week 	 */
specifier|private
specifier|final
specifier|static
name|long
name|VERIFICATION_TIME
init|=
literal|7
operator|*
literal|24
operator|*
literal|3600
decl_stmt|;
annotation|@
name|Id
specifier|public
name|Long
name|id
decl_stmt|;
annotation|@
name|Column
argument_list|(
name|unique
operator|=
literal|true
argument_list|)
specifier|public
name|String
name|token
decl_stmt|;
annotation|@
name|ManyToOne
specifier|public
name|User
name|targetUser
decl_stmt|;
specifier|public
name|Type
name|type
decl_stmt|;
annotation|@
name|Formats
operator|.
name|DateTime
argument_list|(
name|pattern
operator|=
literal|"yyyy-MM-dd HH:mm:ss"
argument_list|)
specifier|public
name|Date
name|created
decl_stmt|;
annotation|@
name|Formats
operator|.
name|DateTime
argument_list|(
name|pattern
operator|=
literal|"yyyy-MM-dd HH:mm:ss"
argument_list|)
specifier|public
name|Date
name|expires
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Finder
argument_list|<
name|Long
argument_list|,
name|TokenAction
argument_list|>
name|find
init|=
operator|new
name|Finder
argument_list|<
name|Long
argument_list|,
name|TokenAction
argument_list|>
argument_list|(
name|Long
operator|.
name|class
argument_list|,
name|TokenAction
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|TokenAction
name|findByToken
parameter_list|(
specifier|final
name|String
name|token
parameter_list|,
specifier|final
name|Type
name|type
parameter_list|)
block|{
return|return
name|find
operator|.
name|where
argument_list|()
operator|.
name|eq
argument_list|(
literal|"token"
argument_list|,
name|token
argument_list|)
operator|.
name|eq
argument_list|(
literal|"type"
argument_list|,
name|type
argument_list|)
operator|.
name|findUnique
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|void
name|deleteByUser
parameter_list|(
specifier|final
name|User
name|u
parameter_list|,
specifier|final
name|Type
name|type
parameter_list|)
block|{
name|QueryIterator
argument_list|<
name|TokenAction
argument_list|>
name|iterator
init|=
name|find
operator|.
name|where
argument_list|()
operator|.
name|eq
argument_list|(
literal|"targetUser.id"
argument_list|,
name|u
operator|.
name|id
argument_list|)
operator|.
name|eq
argument_list|(
literal|"type"
argument_list|,
name|type
argument_list|)
operator|.
name|findIterate
argument_list|()
decl_stmt|;
while|while
condition|(
name|iterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Ebean
operator|.
name|delete
argument_list|(
name|iterator
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iterator
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isValid
parameter_list|()
block|{
return|return
name|this
operator|.
name|expires
operator|.
name|after
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|TokenAction
name|create
parameter_list|(
specifier|final
name|Type
name|type
parameter_list|,
specifier|final
name|String
name|token
parameter_list|,
specifier|final
name|User
name|targetUser
parameter_list|)
block|{
specifier|final
name|TokenAction
name|ua
init|=
operator|new
name|TokenAction
argument_list|()
decl_stmt|;
name|ua
operator|.
name|targetUser
operator|=
name|targetUser
expr_stmt|;
name|ua
operator|.
name|token
operator|=
name|token
expr_stmt|;
name|ua
operator|.
name|type
operator|=
name|type
expr_stmt|;
specifier|final
name|Date
name|created
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|ua
operator|.
name|created
operator|=
name|created
expr_stmt|;
name|ua
operator|.
name|expires
operator|=
operator|new
name|Date
argument_list|(
name|created
operator|.
name|getTime
argument_list|()
operator|+
name|VERIFICATION_TIME
operator|*
literal|1000
argument_list|)
expr_stmt|;
name|ua
operator|.
name|save
argument_list|()
expr_stmt|;
return|return
name|ua
return|;
block|}
block|}
end_class

end_unit

