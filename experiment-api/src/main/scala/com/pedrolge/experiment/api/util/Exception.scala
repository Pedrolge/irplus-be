package com.pedrolge.experiment.api.util

import com.lightbend.lagom.scaladsl.api.transport.{ExceptionMessage, TransportErrorCode, TransportException}

object Exception {


  final class Conflict(errorCode: TransportErrorCode, exceptionMessage: ExceptionMessage, cause: Throwable)
    extends TransportException(errorCode, exceptionMessage, cause)

  object Conflict {
    val ConflictErrorCode: TransportErrorCode = TransportErrorCode(409, 1003, "Conflict")

    def apply(message: String = "") = new Conflict(
      ConflictErrorCode,
      new ExceptionMessage(classOf[Conflict].getSimpleName, message),
      null
    )

    def apply(cause: Throwable) = new Conflict(
      ConflictErrorCode,
      new ExceptionMessage(classOf[Conflict].getSimpleName, cause.getMessage),
      cause
    )
  }
}
