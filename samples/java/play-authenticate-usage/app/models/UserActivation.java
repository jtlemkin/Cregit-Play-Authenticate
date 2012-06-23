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
name|play
operator|.
name|db
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

begin_class
annotation|@
name|Entity
specifier|public
class|class
name|UserActivation
extends|extends
name|Model
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
name|unverified
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
name|UserActivation
argument_list|>
name|find
init|=
operator|new
name|Finder
argument_list|<
name|Long
argument_list|,
name|UserActivation
argument_list|>
argument_list|(
name|Long
operator|.
name|class
argument_list|,
name|UserActivation
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|UserActivation
name|findByToken
parameter_list|(
specifier|final
name|String
name|token
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
parameter_list|)
block|{
name|Ebean
operator|.
name|delete
argument_list|(
name|find
operator|.
name|where
argument_list|()
operator|.
name|eq
argument_list|(
literal|"unverified.id"
argument_list|,
name|u
operator|.
name|id
argument_list|)
operator|.
name|findIterate
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

