package org.example

import android.annotation.TargetApi
import android.app.{Activity,Dialog}
import android.content.Context
import android.content.res.{TypedArray,XmlResourceParser}
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.{View,ViewGroup,LayoutInflater}
import android.view.animation.{Animation, AnimationUtils, Interpolator}
import android.animation.{Animator, AnimatorInflater}

import scala.annotation.implicitNotFound

case class TypedResource[A](id: Int) extends AnyVal
case class TypedLayout[A](id: Int) extends AnyVal
case class TypedRes[A](resid: Int) extends AnyVal {
  def value(implicit ev: TypedResource.TypedResValueOp[A], c: Context): ev.T = ev.resourceValue(resid)(c)
}

object TR {
  final val editText2 = TypedResource[android.widget.EditText](R.id.editText2)
  final val textView0 = TypedResource[android.widget.TextView](R.id.textView0)
  final val editText1 = TypedResource[android.widget.EditText](R.id.editText1)
  final val textView1 = TypedResource[android.widget.TextView](R.id.textView1)
  final val textView2 = TypedResource[android.widget.TextView](R.id.textView2)
  final val button1 = TypedResource[android.widget.Button](R.id.button1)

  object layout {
    final val main = TypedLayout[android.widget.LinearLayout](R.layout.main)
  }


  object drawable {
    final val ic_launcher = TypedRes[TypedResource.ResDrawable](R.drawable.ic_launcher)
  }
  object string {
    final val app_name = TypedRes[TypedResource.ResString](R.string.app_name)
  }
}

trait TypedFindView extends Any {
  protected def findViewById(id: Int): View
  final def findView[A](tr: TypedResource[A]): A = findViewById(tr.id).asInstanceOf[A]
}

object TypedResource {
  sealed trait ResAnim
  sealed trait ResAnimator
  sealed trait ResAttr
  sealed trait ResBool
  sealed trait ResColor
  sealed trait ResDimen
  sealed trait ResDrawable
  sealed trait ResFraction
  sealed trait ResInteger
  sealed trait ResInterpolator
  sealed trait ResMenu
  sealed trait ResMipMap
  sealed trait ResPlurals
  sealed trait ResRaw
  sealed trait ResString
  sealed trait ResStyle
  sealed trait ResTransition
  sealed trait ResXml

  // specializations of ResArray
  sealed trait ResStringArray
  sealed trait ResIntegerArray
  sealed trait ResArray

  @implicitNotFound("don't know how to .value for ${A}, create a TypedResValueOp[${A}] manually")
  trait TypedResValueOp[A] {
    type T
    def resourceValue(resid: Int)(implicit c: Context): T
  }

  implicit class TypedView(val v: View) extends AnyVal with TypedFindView {
    def findViewById(id: Int) = v.findViewById(id)
  }
  implicit class TypedActivity(val a: Activity) extends AnyVal with TypedFindView {
    def findViewById(id: Int) = a.findViewById(id)
  }
  implicit class TypedDialog(val d: Dialog) extends AnyVal with TypedFindView {
    def findViewById(id: Int) = d.findViewById(id)
  }
  implicit class TypedLayoutInflater(val l: LayoutInflater) extends AnyVal {
    def inflate[A <: View](tl: TypedLayout[A], c: ViewGroup, b: Boolean): A = {
      val v = l.inflate(tl.id, c, b)
      val a = if(c != null && b) c.getChildAt(c.getChildCount - 1) else v
      a.asInstanceOf[A]
    }
    def inflate[A <: View](tl: TypedLayout[A], c: ViewGroup): A =
      inflate(tl, c, true)
    def inflate[A <: View](tl: TypedLayout[A]): A =
      inflate(tl, null, false)
  }

  implicit val trAnimValueOp: TypedResValueOp[ResAnim] { type T = Animation } = new TypedResValueOp[ResAnim] {
    type T = Animation
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      AnimationUtils.loadAnimation(c, resid)
  }
  implicit val trAnimatorValueOp: TypedResValueOp[ResAnimator] { type T = Animator } = new TypedResValueOp[ResAnimator] {
    type T = Animator
    @TargetApi(11)
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      AnimatorInflater.loadAnimator(c, resid)
  }
  implicit val trIntegerArrayValueOp: TypedResValueOp[ResIntegerArray] { type T = Array[Int] } = new TypedResValueOp[ResIntegerArray] {
    type T = Array[Int]
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getIntArray(resid)
  }
  implicit val trStringArrayValueOp: TypedResValueOp[ResStringArray] { type T = Array[String] } = new TypedResValueOp[ResStringArray] {
    type T = Array[String]
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getStringArray(resid)
  }
  implicit val trTypedArrayValueOp: TypedResValueOp[ResArray] { type T = TypedArray } = new TypedResValueOp[ResArray] {
    type T = TypedArray
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.obtainTypedArray(resid)
  }
  implicit val trBoolValueOp: TypedResValueOp[ResBool] { type T = Boolean } = new TypedResValueOp[ResBool] {
    type T = Boolean
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getBoolean(resid)
  }
  implicit val trColorValueOp: TypedResValueOp[ResColor] { type T = Int } = new TypedResValueOp[ResColor] {
    type T = Int
    @TargetApi(23)
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      if (Build.VERSION.SDK_INT >= 23)
        c.getColor(resid)
      else
        c.getResources.getColor(resid)
  }
  implicit val trDimenValueOp: TypedResValueOp[ResDimen] { type T = Int } = new TypedResValueOp[ResDimen] {
    type T = Int
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getDimensionPixelSize(resid)
  }
  implicit val trDrawableValueOp: TypedResValueOp[ResDrawable] { type T = Drawable } = new TypedResValueOp[ResDrawable] {
    type T = Drawable
    @TargetApi(21)
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      if (Build.VERSION.SDK_INT >= 21)
        c.getDrawable(resid)
      else
        c.getResources.getDrawable(resid)
             
  }
  implicit val trIntegerValueOp: TypedResValueOp[ResInteger] { type T = Int } = new TypedResValueOp[ResInteger] {
    type T = Int
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getInteger(resid)
  }
  implicit val trInterpolatorValueOp: TypedResValueOp[ResInterpolator] { type T = Interpolator } = new TypedResValueOp[ResInterpolator] {
    type T = Interpolator
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      AnimationUtils.loadInterpolator(c, resid)
  }
  implicit val trMipMapValueOp: TypedResValueOp[ResMipMap] { type T = Drawable } = new TypedResValueOp[ResMipMap] {
    type T = Drawable
    @TargetApi(21)
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      if (Build.VERSION.SDK_INT >= 21)
        c.getDrawable(resid)
      else
        c.getResources.getDrawable(resid)
             
  }
  implicit val trRawValueOp: TypedResValueOp[ResRaw] { type T = java.io.InputStream } = new TypedResValueOp[ResRaw] {
    type T = java.io.InputStream
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.openRawResource(resid)
  }
  implicit val trStringValueOp: TypedResValueOp[ResString] { type T = String } = new TypedResValueOp[ResString] {
    type T = String
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getString(resid)
  }
  implicit val trXmlValueOp: TypedResValueOp[ResXml] { type T = XmlResourceParser } = new TypedResValueOp[ResXml] {
    type T = XmlResourceParser
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getXml(resid)
  }
}
